package io.github.country.log.inmem.persistence

import arrow.core.Option
import arrow.core.toOption
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.access.LocaleDataExtractor
import io.github.country.log.domain.access.extraction.CountryName

class InMemLocaleDataExtractor : LocaleDataExtractor {
    private val storage = mutableMapOf<Pair<String, String>, CountryName>()
        .apply {
            this["UA" to "EN"] = CountryName("Ukraine")
            this["UA" to "UA"] = CountryName("Україна")

            this["UK" to "EN"] = CountryName("Great Britain")
            this["UK" to "UA"] = CountryName("Великобританія")
        }

    override fun extract(country: CountryCode, language: LanguageCode): Option<CountryName> =
        storage[country.asString() to language.asString()].toOption()
}
