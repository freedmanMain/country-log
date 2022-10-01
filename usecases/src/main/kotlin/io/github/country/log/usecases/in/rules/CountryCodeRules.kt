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
import io.github.country.log.usecases.`in`.InputCountryField
import io.github.country.log.usecases.common.UseCaseError
import io.github.country.log.usecases.service.CountryCodeAlreadyExists

sealed class CountryCodeErrors : UseCaseError {
    object EmptyCountryCode : CountryCodeErrors()
    object CountryCodeNotExists : CountryCodeErrors()
    object NotAnCountryCode : CountryCodeErrors()
}

internal object CountryCodeRules {

    private fun InputCountryField.isNotEmptyNel(): ValidatedNel<CountryCodeErrors.EmptyCountryCode, InputCountryField> =
        if (this.value.isBlank())
            CountryCodeErrors.EmptyCountryCode.invalidNel()
        else
            validNel()

    private fun InputCountryField.isNotEmpty(): Validated<CountryCodeErrors.EmptyCountryCode, InputCountryField> =
        if (this.value.isBlank())
            CountryCodeErrors.EmptyCountryCode.invalid()
        else
            valid()

    private fun InputCountryField.isExistsNel(isExists: CountryCodeAlreadyExists)
            : ValidatedNel<CountryCodeErrors.CountryCodeNotExists, InputCountryField> =
        if (!isExists.check(this))
            CountryCodeErrors.CountryCodeNotExists.invalidNel()
        else
            validNel()

    private fun InputCountryField.isExists(isExists: CountryCodeAlreadyExists)
            : Validated<CountryCodeErrors.CountryCodeNotExists, InputCountryField> =
        if (!isExists.check(this))
            CountryCodeErrors.CountryCodeNotExists.invalid()
        else
            valid()

    private fun InputCountryField.validateErrorAccumulate(isExists: CountryCodeAlreadyExists)
            : ValidatedNel<CountryCodeErrors, CountryCode> =
        isNotEmptyNel().zip(
            Semigroup.nonEmptyList(),
            isExistsNel(isExists)
        ) { _, _ -> CountryCode(value) }
            .handleErrorWith { CountryCodeErrors.NotAnCountryCode.invalidNel() }

    private fun InputCountryField.validateFailFastNel(isExists: CountryCodeAlreadyExists)
            : Either<Nel<CountryCodeErrors>, CountryCode> = either.eager {
        isNotEmptyNel().bind()
        isExistsNel(isExists).bind()
        CountryCode(value)
    }

    private fun InputCountryField.validateFailFast(isExists: CountryCodeAlreadyExists)
            : Either<CountryCodeErrors, CountryCode> = either.eager {
        isNotEmpty().bind()
        isExists(isExists).bind()
        CountryCode(value)
    }

    operator fun invoke(
        isExists: CountryCodeAlreadyExists,
        field: InputCountryField
    ): Either<CountryCodeErrors, CountryCode> = field.validateFailFast(isExists)

    operator fun invoke(
        strategy: Strategy,
        isExists: CountryCodeAlreadyExists,
        fields: List<InputCountryField>
    ): Either<Nel<CountryCodeErrors>, List<CountryCode>> = when (strategy) {
        is Strategy.FailFast -> fields.traverse { it.validateFailFastNel(isExists) }

        is Strategy.ErrorAccumulation -> fields.traverse { it.validateErrorAccumulate(isExists) }.toEither()
    }
}
