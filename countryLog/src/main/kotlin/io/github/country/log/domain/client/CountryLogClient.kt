package io.github.country.log.domain.client

import arrow.core.Either
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.model.errors.LocaleNameNotFoundError
import io.github.country.log.domain.services.result.LocaleName

public interface CountryLogClient {
    public fun findLocaleName(
        country: CountryCode,
        language: LanguageCode
    ): Either<LocaleNameNotFoundError, LocaleName>
}
