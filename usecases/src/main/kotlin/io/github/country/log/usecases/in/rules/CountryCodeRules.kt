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
import io.github.country.log.usecases.`in`.CountryCode
import io.github.country.log.usecases.`in`.CountryCodeInput
import io.github.country.log.usecases.common.UseCaseError
import io.github.country.log.usecases.services.CountryCodeAlreadyExists

sealed class CountryCodeErrors : UseCaseError {
    object EmptyCountryCode : CountryCodeErrors()
    object CountryCodeNotExists : CountryCodeErrors()
    object NotAnCountryCode : CountryCodeErrors()
}

internal object CountryCodeRules {

    private fun CountryCodeInput.isNotEmptyNel(): ValidatedNel<CountryCodeErrors.EmptyCountryCode, CountryCodeInput> =
        if (this.value.isBlank())
            CountryCodeErrors.EmptyCountryCode.invalidNel()
        else
            validNel()

    private fun CountryCodeInput.isNotEmpty(): Validated<CountryCodeErrors.EmptyCountryCode, CountryCodeInput> =
        if (this.value.isBlank())
            CountryCodeErrors.EmptyCountryCode.invalid()
        else
            valid()

    private fun CountryCodeInput.isExistsNel(isExists: CountryCodeAlreadyExists)
            : ValidatedNel<CountryCodeErrors.CountryCodeNotExists, CountryCodeInput> =
        if (!isExists.check(this))
            CountryCodeErrors.CountryCodeNotExists.invalidNel()
        else
            validNel()

    private fun CountryCodeInput.isExists(isExists: CountryCodeAlreadyExists)
            : Validated<CountryCodeErrors.CountryCodeNotExists, CountryCodeInput> =
        if (!isExists.check(this))
            CountryCodeErrors.CountryCodeNotExists.invalid()
        else
            valid()

    private fun CountryCodeInput.validateErrorAccumulate(isExists: CountryCodeAlreadyExists)
            : ValidatedNel<CountryCodeErrors, CountryCode> =
        isNotEmptyNel().zip(
            Semigroup.nonEmptyList(),
            isExistsNel(isExists)
        ) { _, _ -> CountryCode(value) }
            .handleErrorWith { CountryCodeErrors.NotAnCountryCode.invalidNel() }

    private fun CountryCodeInput.validateFailFastNel(isExists: CountryCodeAlreadyExists)
            : Either<Nel<CountryCodeErrors>, CountryCode> = either.eager {
        isNotEmptyNel().bind()
        isExistsNel(isExists).bind()
        CountryCode(value)
    }

    private fun CountryCodeInput.validateFailFast(isExists: CountryCodeAlreadyExists)
            : Either<CountryCodeErrors, CountryCode> = either.eager {
        isNotEmpty().bind()
        isExists(isExists).bind()
        CountryCode(value)
    }

    operator fun invoke(
        isExists: CountryCodeAlreadyExists,
        field: CountryCodeInput
    ): Either<CountryCodeErrors, CountryCode> = field.validateFailFast(isExists)

    operator fun invoke(
        strategy: Strategy,
        isExists: CountryCodeAlreadyExists,
        fields: List<CountryCodeInput>
    ): Either<Nel<CountryCodeErrors>, List<CountryCode>> = when (strategy) {
        is Strategy.FailFast -> fields.traverse { it.validateFailFastNel(isExists) }

        is Strategy.ErrorAccumulation -> fields.traverse { it.validateErrorAccumulate(isExists) }.toEither()
    }
}
