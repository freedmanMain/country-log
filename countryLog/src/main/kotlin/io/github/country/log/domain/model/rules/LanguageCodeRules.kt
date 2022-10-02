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
import io.github.country.log.domain.model.errors.LanguageCodeCreationErrors
import io.github.country.log.domain.services.LanguageAlreadyExists

internal object LanguageCodeRules {
    private fun String.notBlank(): Validated<LanguageCodeCreationErrors.BlankLanguageCodeError, String> =
        if (this.isBlank())
            LanguageCodeCreationErrors.BlankLanguageCodeError.invalid()
        else
            valid()

    private fun String.notBlankNel(): ValidatedNel<LanguageCodeCreationErrors.BlankLanguageCodeError, String> =
        if (this.isBlank())
            LanguageCodeCreationErrors.BlankLanguageCodeError.invalidNel()
        else
            validNel()

    private fun String.notUnknownNel(languageAlreadyExists: LanguageAlreadyExists): ValidatedNel<LanguageCodeCreationErrors.LanguageCodeNotExistsError, String> =
        if (!languageAlreadyExists.check(this))
            LanguageCodeCreationErrors.LanguageCodeNotExistsError.invalidNel()
        else
            validNel()

    private fun String.notUnknown(languageAlreadyExists: LanguageAlreadyExists)
            : Validated<LanguageCodeCreationErrors.LanguageCodeNotExistsError, String> = if (!languageAlreadyExists.check(this))
        LanguageCodeCreationErrors.LanguageCodeNotExistsError.invalid()
    else
        valid()

    private fun String.validateFailFast(languageAlreadyExists: LanguageAlreadyExists)
            : Either<LanguageCodeCreationErrors, LanguageCode> = either.eager {
        notBlank().bind()
        notUnknown(languageAlreadyExists).bind()
        LanguageCode(this@validateFailFast)
    }

    private fun String.validateFailFastNel(languageAlreadyExists: LanguageAlreadyExists)
            : Either<Nel<LanguageCodeCreationErrors>, LanguageCode> = either.eager {
        notBlankNel().bind()
        notUnknownNel(languageAlreadyExists).bind()
        LanguageCode(this@validateFailFastNel)
    }

    private fun String.validateErrorAccumulate(languageAlreadyExists: LanguageAlreadyExists)
            : ValidatedNel<LanguageCodeCreationErrors, LanguageCode> = notBlankNel().zip(
        Semigroup.nonEmptyList(),
        notUnknownNel(languageAlreadyExists)
    ) { _, _ -> LanguageCode(this@validateErrorAccumulate) }
        .handleErrorWith { LanguageCodeCreationErrors.NotAtLanguageError.invalidNel() }

    internal operator fun invoke(
        languageAlreadyExists: LanguageAlreadyExists,
        data: String
    ): Either<LanguageCodeCreationErrors, LanguageCode> = data.validateFailFast(languageAlreadyExists)

    internal operator fun invoke(
        strategy: ValidationStrategy,
        languageAlreadyExists: LanguageAlreadyExists,
        dataList: List<String>
    ): Either<Nel<LanguageCodeCreationErrors>, List<LanguageCode>> = when (strategy) {
        is ValidationStrategy.FailFast -> dataList.traverse { it.validateFailFastNel(languageAlreadyExists) }
        is ValidationStrategy.ErrorAccumulation -> dataList.traverse { it.validateErrorAccumulate(languageAlreadyExists) }
            .toEither()
    }
}
