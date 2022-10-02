package io.github.country.log.domain.fixtures

import arrow.core.Option
import arrow.core.toOption
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.extractor.LocaleDataExtractor
import io.github.country.log.domain.services.result.LocaleName

internal class InMemLocaleDataExtractor : LocaleDataExtractor {
    private val inMemDb = mutableMapOf<Pair<CountryCode, LanguageCode>, LocaleName>()
        .apply {
            this[CountryCode("UA") to LanguageCode("EN")] = LocaleName("Ukraine")
            this[CountryCode("UK") to LanguageCode("EN")] = LocaleName("United Kingdom")
        }

    override fun extract(country: CountryCode, language: LanguageCode): Option<LocaleName> =
        inMemDb[country to language].toOption()
}
