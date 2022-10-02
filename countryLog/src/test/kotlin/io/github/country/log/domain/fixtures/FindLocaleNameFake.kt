package io.github.country.log.domain.fixtures

import arrow.core.None
import arrow.core.Option
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.services.result.LocaleName
import io.github.country.log.domain.services.FindLocaleName

class FindLocaleNameFake : FindLocaleName {
    private val inMemDb = mutableMapOf<Pair<CountryCode, LanguageCode>, String>()
        .apply {
            this[CountryCode("UA") to LanguageCode("EN")] = "Ukraine"
            this[CountryCode("UK") to LanguageCode("EN")] = "United Kingdom"
        }

    override fun find(country: CountryCode, language: LanguageCode): Option<LocaleName> =
        inMemDb[country to language]
            ?.let { Option(LocaleName(it)) }
            ?: None
}
