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
import io.github.country.log.domain.model.CountryCodeRequest
import io.github.country.log.domain.model.errors.CountryCodeCreationErrors
import io.github.country.log.domain.services.CountryAlreadyExists

internal object CountryCodeRules {

    private fun CountryCodeRequest.notBlankNel(): ValidatedNel<CountryCodeCreationErrors.BlankCountryCodeError, CountryCodeRequest> =
        if (this.value.isBlank())
            CountryCodeCreationErrors.BlankCountryCodeError.invalidNel()
        else
            validNel()

    private fun CountryCodeRequest.notBlank(): Validated<CountryCodeCreationErrors.BlankCountryCodeError, CountryCodeRequest> =
        if (this.value.isBlank())
            CountryCodeCreationErrors.BlankCountryCodeError.invalid()
        else
            valid()

    private fun CountryCodeRequest.notUnknownNel(countryAlreadyExists: CountryAlreadyExists)
            : ValidatedNel<CountryCodeCreationErrors.CountryCodeNotExistsError, CountryCodeRequest> =
        if (!countryAlreadyExists.check(this))
            CountryCodeCreationErrors.CountryCodeNotExistsError.invalidNel()
        else
            validNel()

    private fun CountryCodeRequest.notUnknown(countryAlreadyExists: CountryAlreadyExists)
            : Validated<CountryCodeCreationErrors.CountryCodeNotExistsError, CountryCodeRequest> =
        if (!countryAlreadyExists.check(this))
            CountryCodeCreationErrors.CountryCodeNotExistsError.invalid()
        else
            valid()

    private fun CountryCodeRequest.validateErrorAccumulate(countryAlreadyExists: CountryAlreadyExists)
            : ValidatedNel<CountryCodeCreationErrors, CountryCode> =
        notBlankNel().zip(
            Semigroup.nonEmptyList(),
            notUnknownNel(countryAlreadyExists)
        ) { _, _ -> CountryCode(value) }
            .handleErrorWith { CountryCodeCreationErrors.NotAtCountryCodeError.invalidNel() }

    private fun CountryCodeRequest.validateFailFastNel(countryAlreadyExists: CountryAlreadyExists)
            : Either<Nel<CountryCodeCreationErrors>, CountryCode> = either.eager {
        notBlankNel().bind()
        notUnknownNel(countryAlreadyExists).bind()
        CountryCode(value)
    }

    private fun CountryCodeRequest.validateFailFast(countryAlreadyExists: CountryAlreadyExists)
            : Either<CountryCodeCreationErrors, CountryCode> = either.eager {
        notBlank().bind()
        notUnknown(countryAlreadyExists).bind()
        CountryCode(value)
    }

    internal operator fun invoke(
        countryAlreadyExists: CountryAlreadyExists,
        request: CountryCodeRequest
    ): Either<CountryCodeCreationErrors, CountryCode> = request.validateFailFast(countryAlreadyExists)

    internal operator fun invoke(
        strategy: ValidationStrategy,
        countryAlreadyExists: CountryAlreadyExists,
        requestList: List<CountryCodeRequest>
    ): Either<Nel<CountryCodeCreationErrors>, List<CountryCode>> = when (strategy) {
        is ValidationStrategy.FailFast -> requestList.traverse { it.validateFailFastNel(countryAlreadyExists) }
        is ValidationStrategy.ErrorAccumulation -> requestList.traverse { it.validateErrorAccumulate(countryAlreadyExists) }
            .toEither()
    }
}
