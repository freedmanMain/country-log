package io.github.country.log.domain.fixtures

import arrow.core.Either
import io.github.country.log.domain.client.CountryLogClient
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.model.errors.LocaleNameNotFoundError
import io.github.country.log.domain.services.FindLocaleName
import io.github.country.log.domain.services.result.LocaleName

class CountryLogClientFake(
    private val findLocaleName: FindLocaleName
) : CountryLogClient {
    override fun findLocaleName(
        country: CountryCode,
        language: LanguageCode
    ): Either<LocaleNameNotFoundError, LocaleName> =
        findLocaleName.find(country, language)
            .toEither { LocaleNameNotFoundError }
}
