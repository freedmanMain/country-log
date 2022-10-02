package io.github.country.log.domain.usecase.scenarious

import arrow.core.Either
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.CountryRepository
import io.github.country.log.domain.model.errors.CountryCodeCreationErrors
import io.github.country.log.domain.usecase.CreateCountryCode
import io.github.country.log.domain.usecase.CreateCountryCodeUseCaseErrors

public class CreateCountryCodeUseCase(
    private val countryRepository: CountryRepository
) : CreateCountryCode {

    public override fun execute(value: String): Either<CreateCountryCodeUseCaseErrors, CountryCode> =
        CountryCode.make(value, countryRepository).mapLeft { error ->
            when (error) {
                is CountryCodeCreationErrors.BlankCountryCodeError -> error.toError()
                is CountryCodeCreationErrors.CountryCodeNotExistsError -> error.toError()
                is CountryCodeCreationErrors.NotAtCountryCodeError -> error.toError()
            }
        }
}

internal fun CountryCodeCreationErrors.BlankCountryCodeError.toError()
        : CreateCountryCodeUseCaseErrors.EmptyCountryCode =
    CreateCountryCodeUseCaseErrors.EmptyCountryCode("Blank country code value")

internal fun CountryCodeCreationErrors.CountryCodeNotExistsError.toError()
        : CreateCountryCodeUseCaseErrors.UnknownCountryCode =
    CreateCountryCodeUseCaseErrors.UnknownCountryCode("Unknown country code")

internal fun CountryCodeCreationErrors.NotAtCountryCodeError.toError()
        : CreateCountryCodeUseCaseErrors.UnknownCountryCode =
    CreateCountryCodeUseCaseErrors.UnknownCountryCode("Not at country code passed")

