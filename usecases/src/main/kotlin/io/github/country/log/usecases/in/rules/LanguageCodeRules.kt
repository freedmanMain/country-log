package io.github.country.log.usecases.`in`.rules

import arrow.core.Either
import arrow.core.Nel
import arrow.core.Validated
import arrow.core.ValidatedNel
import arrow.core.continuations.either
import arrow.core.handleErrorWith
import arrow.core.invalid
import arrow.core.invalidNel
import arrow.core.traverse
import arrow.core.valid
import arrow.core.validNel
import arrow.core.zip
import arrow.typeclasses.Semigroup
import io.github.country.log.usecases.`in`.LanguageCode
import io.github.country.log.usecases.`in`.LanguageCodeInput
import io.github.country.log.usecases.common.UseCaseError
import io.github.country.log.usecases.service.LanguageCodeAlreadyExists

sealed class LanguageCodeErrors : UseCaseError {
    object EmptyLanguageCode : LanguageCodeErrors()
    object LanguageCodeNotExists : LanguageCodeErrors()
    object NotAtLanguage : LanguageCodeErrors()
}

internal object LanguageCodeRules {
    private fun LanguageCodeInput.isNotEmpty(): Validated<LanguageCodeErrors.EmptyLanguageCode, LanguageCodeInput> =
        if (this.value.isBlank())
            LanguageCodeErrors.EmptyLanguageCode.invalid()
        else
            valid()

    private fun LanguageCodeInput.isNotEmptyNel(): ValidatedNel<LanguageCodeErrors.EmptyLanguageCode, LanguageCodeInput> =
        if (this.value.isBlank())
            LanguageCodeErrors.EmptyLanguageCode.invalidNel()
        else
            validNel()

    private fun LanguageCodeInput.isExistsNel(isExists: LanguageCodeAlreadyExists): ValidatedNel<LanguageCodeErrors.LanguageCodeNotExists, LanguageCodeInput> =
        if (!isExists.check(this))
            LanguageCodeErrors.LanguageCodeNotExists.invalidNel()
        else
            validNel()

    private fun LanguageCodeInput.isExists(isExists: LanguageCodeAlreadyExists)
            : Validated<LanguageCodeErrors.LanguageCodeNotExists, LanguageCodeInput> = if (!isExists.check(this))
        LanguageCodeErrors.LanguageCodeNotExists.invalid()
    else
        valid()

    private fun LanguageCodeInput.validateFailFast(isExists: LanguageCodeAlreadyExists)
            : Either<LanguageCodeErrors, LanguageCode> = either.eager {
        isNotEmpty().bind()
        isExists(isExists).bind()
        LanguageCode(value)
    }

    private fun LanguageCodeInput.validateFailFastNel(isExists: LanguageCodeAlreadyExists)
            : Either<Nel<LanguageCodeErrors>, LanguageCode> = either.eager {
        isNotEmptyNel().bind()
        isExistsNel(isExists).bind()
        LanguageCode(value)
    }

    private fun LanguageCodeInput.validateErrorAccumulate(isExists: LanguageCodeAlreadyExists)
            : ValidatedNel<LanguageCodeErrors, LanguageCode> = isNotEmptyNel().zip(
        Semigroup.nonEmptyList(),
        isExistsNel(isExists)
    ) { _, _ -> LanguageCode(value) }
        .handleErrorWith { LanguageCodeErrors.NotAtLanguage.invalidNel() }

    operator fun invoke(
        isExists: LanguageCodeAlreadyExists,
        field: LanguageCodeInput
    ): Either<LanguageCodeErrors, LanguageCode> = field.validateFailFast(isExists)

    operator fun invoke(
        strategy: Strategy,
        isExists: LanguageCodeAlreadyExists,
        fields: List<LanguageCodeInput>
    ): Either<Nel<LanguageCodeErrors>, List<LanguageCode>> = when (strategy) {
        is Strategy.FailFast -> fields.traverse { it.validateFailFastNel(isExists) }
        is Strategy.ErrorAccumulation -> fields.traverse { it.validateErrorAccumulate(isExists) }
            .toEither()
    }
}
