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
import io.github.country.log.domain.model.errors.CountryCodeCreationErrors
import io.github.country.log.domain.services.CountryAlreadyExists

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

    private fun String.notUnknownNel(countryAlreadyExists: CountryAlreadyExists)
            : ValidatedNel<CountryCodeCreationErrors.CountryCodeNotExistsError, String> =
        if (!countryAlreadyExists.check(this))
            CountryCodeCreationErrors.CountryCodeNotExistsError.invalidNel()
        else
            validNel()

    private fun String.notUnknown(countryAlreadyExists: CountryAlreadyExists)
            : Validated<CountryCodeCreationErrors.CountryCodeNotExistsError, String> =
        if (!countryAlreadyExists.check(this))
            CountryCodeCreationErrors.CountryCodeNotExistsError.invalid()
        else
            valid()

    private fun String.validateErrorAccumulate(countryAlreadyExists: CountryAlreadyExists)
            : ValidatedNel<CountryCodeCreationErrors, CountryCode> =
        notBlankNel().zip(
            Semigroup.nonEmptyList(),
            notUnknownNel(countryAlreadyExists)
        ) { _, _ -> CountryCode(this@validateErrorAccumulate) }
            .handleErrorWith { CountryCodeCreationErrors.NotAtCountryCodeError.invalidNel() }

    private fun String.validateFailFastNel(countryAlreadyExists: CountryAlreadyExists)
            : Either<Nel<CountryCodeCreationErrors>, CountryCode> = either.eager {
        notBlankNel().bind()
        notUnknownNel(countryAlreadyExists).bind()
        CountryCode(this@validateFailFastNel)
    }

    private fun String.validateFailFast(countryAlreadyExists: CountryAlreadyExists)
            : Either<CountryCodeCreationErrors, CountryCode> = either.eager {
        notBlank().bind()
        notUnknown(countryAlreadyExists).bind()
        CountryCode(this@validateFailFast)
    }

    internal operator fun invoke(
        countryAlreadyExists: CountryAlreadyExists,
        data: String
    ): Either<CountryCodeCreationErrors, CountryCode> = data.validateFailFast(countryAlreadyExists)

    internal operator fun invoke(
        strategy: ValidationStrategy,
        countryAlreadyExists: CountryAlreadyExists,
        dataList: List<String>
    ): Either<Nel<CountryCodeCreationErrors>, List<CountryCode>> = when (strategy) {
        is ValidationStrategy.FailFast -> dataList.traverse { it.validateFailFastNel(countryAlreadyExists) }
        is ValidationStrategy.ErrorAccumulation -> dataList.traverse { it.validateErrorAccumulate(countryAlreadyExists) }
            .toEither()
    }
}
