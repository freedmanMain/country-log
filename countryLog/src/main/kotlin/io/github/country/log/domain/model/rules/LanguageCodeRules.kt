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
import io.github.country.log.domain.model.LanguageRepository
import io.github.country.log.domain.model.errors.LanguageCodeCreationErrors

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

    private fun String.alreadyExistsNel(repository: LanguageRepository): ValidatedNel<LanguageCodeCreationErrors.LanguageCodeNotExistsError, String> =
        if (!repository.alreadyExists(this))
            LanguageCodeCreationErrors.LanguageCodeNotExistsError.invalidNel()
        else
            validNel()

    private fun String.alreadyExists(repository: LanguageRepository)
            : Validated<LanguageCodeCreationErrors.LanguageCodeNotExistsError, String> = if (!repository.alreadyExists(this))
        LanguageCodeCreationErrors.LanguageCodeNotExistsError.invalid()
    else
        valid()

    private fun String.validateFailFast(repository: LanguageRepository)
            : Either<LanguageCodeCreationErrors, LanguageCode> = either.eager {
        notBlank().bind()
        alreadyExists(repository).bind()
        LanguageCode(this@validateFailFast)
    }

    private fun String.validateFailFastNel(repository: LanguageRepository)
            : Either<Nel<LanguageCodeCreationErrors>, LanguageCode> = either.eager {
        notBlankNel().bind()
        alreadyExistsNel(repository).bind()
        LanguageCode(this@validateFailFastNel)
    }

    private fun String.validateErrorAccumulate(repository: LanguageRepository)
            : ValidatedNel<LanguageCodeCreationErrors, LanguageCode> = notBlankNel().zip(
        Semigroup.nonEmptyList(),
        alreadyExistsNel(repository)
    ) { _, _ -> LanguageCode(this@validateErrorAccumulate) }
        .handleErrorWith { LanguageCodeCreationErrors.NotAtLanguageError.invalidNel() }

    internal operator fun invoke(
        repository: LanguageRepository,
        data: String
    ): Either<LanguageCodeCreationErrors, LanguageCode> = data.validateFailFast(repository)

    internal operator fun invoke(
        strategy: ValidationStrategy,
        repository: LanguageRepository,
        dataList: List<String>
    ): Either<Nel<LanguageCodeCreationErrors>, List<LanguageCode>> = when (strategy) {
        is ValidationStrategy.FailFast -> dataList.traverse { it.validateFailFastNel(repository) }
        is ValidationStrategy.ErrorAccumulation -> dataList.traverse { it.validateErrorAccumulate(repository) }
            .toEither()
    }
}
