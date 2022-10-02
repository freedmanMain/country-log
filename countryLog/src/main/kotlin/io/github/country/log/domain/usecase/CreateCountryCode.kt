package io.github.country.log.domain.usecase

import arrow.core.Either
import io.github.country.log.domain.model.CountryCode

public interface CreateCountryCode {
    public fun execute(value: String): Either<CreateCountryCodeUseCaseErrors, CountryCode>
}

public sealed class CreateCountryCodeUseCaseErrors {
    public abstract val message: String

    public data class EmptyCountryCode(override val message: String) : CreateCountryCodeUseCaseErrors()
    public data class UnknownCountryCode(override val message: String) : CreateCountryCodeUseCaseErrors()
}

