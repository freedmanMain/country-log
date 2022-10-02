package io.github.country.log.domain.fixtures

import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.extractor.CountryExtractor

class InMemCountryExtractor : CountryExtractor {
    private val inMemDb = mutableMapOf<Pair<CountryCode, LanguageCode>, String>()
        .apply {
            this[CountryCode("UA") to LanguageCode("EN")] = "Ukraine"
            this[CountryCode("UK") to LanguageCode("EN")] = "United Kingdom"
        }

    override fun findLocaleName(country: CountryCode, language: LanguageCode): String? =
        inMemDb[country to language]
}
