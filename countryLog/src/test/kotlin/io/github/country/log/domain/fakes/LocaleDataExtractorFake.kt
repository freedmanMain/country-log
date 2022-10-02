package io.github.country.log.domain.fakes

import arrow.core.Option
import arrow.core.toOption
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.access.LocaleDataExtractor
import io.github.country.log.domain.access.extraction.CountryName

internal class LocaleDataExtractorFake : LocaleDataExtractor {
    private val inMemDb = mutableMapOf<Pair<CountryCode, LanguageCode>, CountryName>()
        .apply {
            this[CountryCode("UA") to LanguageCode("EN")] = CountryName("Ukraine")
            this[CountryCode("UK") to LanguageCode("EN")] = CountryName("United Kingdom")
        }

    override fun extract(country: CountryCode, language: LanguageCode): Option<CountryName> =
        inMemDb[country to language].toOption()
}
