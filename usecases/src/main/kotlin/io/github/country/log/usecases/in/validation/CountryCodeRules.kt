package io.github.country.log.usecases.`in`.validation

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
import io.github.country.log.usecases.`in`.CountryFormField
import io.github.country.log.usecases.common.UseCaseError
import io.github.country.log.usecases.service.CountryCodeAlreadyExists

sealed class CountryCodeErrors : UseCaseError {

    object EmptyCountryCode : CountryCodeErrors()

    object CountryCodeNotExists : CountryCodeErrors()

    object NotAnCountryCode : CountryCodeErrors()
}

internal object CountryCodeRules {

    private fun CountryFormField.isNotEmptyNel(): ValidatedNel<CountryCodeErrors, CountryFormField> =
        if (this.value.isBlank())
            CountryCodeErrors.EmptyCountryCode.invalidNel()
        else
            validNel()

    private fun CountryFormField.isNotEmpty(): Validated<CountryCodeErrors, CountryFormField> =
        if (this.value.isBlank())
            CountryCodeErrors.EmptyCountryCode.invalid()
        else
            valid()


    private fun CountryFormField.isExistsNel(isExists: CountryCodeAlreadyExists): ValidatedNel<CountryCodeErrors, CountryFormField> =
        if (!isExists.check(this))
            CountryCodeErrors.CountryCodeNotExists.invalidNel()
        else
            validNel()

    private fun CountryFormField.isExists(isExists: CountryCodeAlreadyExists): Validated<CountryCodeErrors, CountryFormField> =
        if (!isExists.check(this))
            CountryCodeErrors.CountryCodeNotExists.invalid()
        else
            valid()

    private fun CountryFormField.validateErrorAccumulate(isExists: CountryCodeAlreadyExists)
            : ValidatedNel<CountryCodeErrors, CountryCode> =
        isNotEmptyNel().zip(
            Semigroup.nonEmptyList(),
            isExistsNel(isExists)
        ) { _, _ -> CountryCode(value) }
            .handleErrorWith { CountryCodeErrors.NotAnCountryCode.invalidNel() }

    private fun CountryFormField.validateFailFastNel(isExists: CountryCodeAlreadyExists)
            : Either<Nel<CountryCodeErrors>, CountryCode> = either.eager {
        isNotEmptyNel().bind()
        isExistsNel(isExists).bind()
        CountryCode(value)
    }

    private fun CountryFormField.validateFailFast(isExists: CountryCodeAlreadyExists)
            : Either<CountryCodeErrors, CountryCode> = either.eager {
        isNotEmpty().bind()
        isExists(isExists).bind()
        CountryCode(value)
    }

    operator fun invoke(
        isExists: CountryCodeAlreadyExists,
        field: CountryFormField
    ): Either<CountryCodeErrors, CountryCode> = field.validateFailFast(isExists)

    operator fun invoke(
        strategy: ValidationStrategy,
        isExists: CountryCodeAlreadyExists,
        fields: List<CountryFormField>
    ): Either<Nel<CountryCodeErrors>, List<CountryCode>> = when (strategy) {
        is ValidationStrategy.FailFast -> fields.traverse { it.validateFailFastNel(isExists) }

        is ValidationStrategy.ErrorAccumulation -> fields.traverse { it.validateErrorAccumulate(isExists) }.toEither()
    }
}
