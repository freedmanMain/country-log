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
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.CountryRepository
import io.github.country.log.domain.model.errors.CountryCodeCreationErrors

internal object CountryCodeRules {

    private fun String.notBlankNel(): ValidatedNel<CountryCodeCreationErrors.BlankCountryCodeError, String> =
        if (this.isBlank())
            CountryCodeCreationErrors.BlankCountryCodeError.invalidNel()
        else
            validNel()

    private fun String.notBlank(): Validated<CountryCodeCreationErrors.BlankCountryCodeError, String> =
        if (this.isBlank())
            CountryCodeCreationErrors.BlankCountryCodeError.invalid()
        else
            valid()

    private fun String.alreadyExistsNel(repository: CountryRepository)
            : ValidatedNel<CountryCodeCreationErrors.CountryCodeNotExistsError, String> =
        if (!repository.alreadyExists(this))
            CountryCodeCreationErrors.CountryCodeNotExistsError.invalidNel()
        else
            validNel()

    private fun String.alreadyExists(repository: CountryRepository)
            : Validated<CountryCodeCreationErrors.CountryCodeNotExistsError, String> =
        if (!repository.alreadyExists(this))
            CountryCodeCreationErrors.CountryCodeNotExistsError.invalid()
        else
            valid()

    private fun String.validateErrorAccumulate(repository: CountryRepository)
            : ValidatedNel<CountryCodeCreationErrors, CountryCode> =
        notBlankNel().zip(
            Semigroup.nonEmptyList(),
            alreadyExistsNel(repository)
        ) { _, _ -> CountryCode(this@validateErrorAccumulate) }
            .handleErrorWith { CountryCodeCreationErrors.NotAtCountryCodeError.invalidNel() }

    private fun String.validateFailFastNel(repository: CountryRepository)
            : Either<Nel<CountryCodeCreationErrors>, CountryCode> = either.eager {
        notBlankNel().bind()
        alreadyExistsNel(repository).bind()
        CountryCode(this@validateFailFastNel)
    }

    private fun String.validateFailFast(repository: CountryRepository)
            : Either<CountryCodeCreationErrors, CountryCode> = either.eager {
        notBlank().bind()
        alreadyExists(repository).bind()
        CountryCode(this@validateFailFast)
    }

    internal operator fun invoke(
        repository: CountryRepository,
        data: String
    ): Either<CountryCodeCreationErrors, CountryCode> = data.validateFailFast(repository)

    internal operator fun invoke(
        strategy: ValidationStrategy,
        repository: CountryRepository,
        dataList: List<String>
    ): Either<Nel<CountryCodeCreationErrors>, List<CountryCode>> = when (strategy) {
        is ValidationStrategy.FailFast -> dataList.traverse { it.validateFailFastNel(repository) }
        is ValidationStrategy.ErrorAccumulation -> dataList.traverse { it.validateErrorAccumulate(repository) }
            .toEither()
    }
}
