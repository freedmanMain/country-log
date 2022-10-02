package io.github.country.log.domain.fixtures

import arrow.core.Either
import io.github.country.log.domain.client.Client
import io.github.country.log.domain.extractor.LocaleDataExtractor
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.model.errors.LocaleNameNotFoundError
import io.github.country.log.domain.services.result.LocaleName

internal class ClientFake(
    private val extractor: LocaleDataExtractor
) : Client {
    override fun provideLocaleName(
        country: CountryCode,
        language: LanguageCode
    ): Either<LocaleNameNotFoundError, LocaleName> =
        extractor.extract(country, language).toEither { LocaleNameNotFoundError }
}
