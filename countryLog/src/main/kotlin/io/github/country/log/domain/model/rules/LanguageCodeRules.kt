package io.github.country.log.domain.model.rules

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
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.model.LanguageCodeRequest
import io.github.country.log.domain.model.errors.LanguageCodeCreationErrors
import io.github.country.log.domain.services.LanguageAlreadyExists

internal object LanguageCodeRules {
    private fun LanguageCodeRequest.notBlank(): Validated<LanguageCodeCreationErrors.BlankLanguageCodeError, LanguageCodeRequest> =
        if (this.value.isBlank())
            LanguageCodeCreationErrors.BlankLanguageCodeError.invalid()
        else
            valid()

    private fun LanguageCodeRequest.notBlankNel(): ValidatedNel<LanguageCodeCreationErrors.BlankLanguageCodeError, LanguageCodeRequest> =
        if (this.value.isBlank())
            LanguageCodeCreationErrors.BlankLanguageCodeError.invalidNel()
        else
            validNel()

    private fun LanguageCodeRequest.notUnknownNel(languageAlreadyExists: LanguageAlreadyExists): ValidatedNel<LanguageCodeCreationErrors.LanguageCodeNotExistsError, LanguageCodeRequest> =
        if (!languageAlreadyExists.check(this))
            LanguageCodeCreationErrors.LanguageCodeNotExistsError.invalidNel()
        else
            validNel()

    private fun LanguageCodeRequest.notUnknown(languageAlreadyExists: LanguageAlreadyExists)
            : Validated<LanguageCodeCreationErrors.LanguageCodeNotExistsError, LanguageCodeRequest> = if (!languageAlreadyExists.check(this))
        LanguageCodeCreationErrors.LanguageCodeNotExistsError.invalid()
    else
        valid()

    private fun LanguageCodeRequest.validateFailFast(languageAlreadyExists: LanguageAlreadyExists)
            : Either<LanguageCodeCreationErrors, LanguageCode> = either.eager {
        notBlank().bind()
        notUnknown(languageAlreadyExists).bind()
        LanguageCode(value)
    }

    private fun LanguageCodeRequest.validateFailFastNel(languageAlreadyExists: LanguageAlreadyExists)
            : Either<Nel<LanguageCodeCreationErrors>, LanguageCode> = either.eager {
        notBlankNel().bind()
        notUnknownNel(languageAlreadyExists).bind()
        LanguageCode(value)
    }

    private fun LanguageCodeRequest.validateErrorAccumulate(languageAlreadyExists: LanguageAlreadyExists)
            : ValidatedNel<LanguageCodeCreationErrors, LanguageCode> = notBlankNel().zip(
        Semigroup.nonEmptyList(),
        notUnknownNel(languageAlreadyExists)
    ) { _, _ -> LanguageCode(value) }
        .handleErrorWith { LanguageCodeCreationErrors.NotAtLanguageError.invalidNel() }

    internal operator fun invoke(
        languageAlreadyExists: LanguageAlreadyExists,
        request: LanguageCodeRequest
    ): Either<LanguageCodeCreationErrors, LanguageCode> = request.validateFailFast(languageAlreadyExists)

    internal operator fun invoke(
        strategy: ValidationStrategy,
        languageAlreadyExists: LanguageAlreadyExists,
        requestList: List<LanguageCodeRequest>
    ): Either<Nel<LanguageCodeCreationErrors>, List<LanguageCode>> = when (strategy) {
        is ValidationStrategy.FailFast -> requestList.traverse { it.validateFailFastNel(languageAlreadyExists) }
        is ValidationStrategy.ErrorAccumulation -> requestList.traverse { it.validateErrorAccumulate(languageAlreadyExists) }
            .toEither()
    }
}
