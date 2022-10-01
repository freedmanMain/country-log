package io.github.country.log.usecases.fixtures

import arrow.core.None
import arrow.core.Option
import io.github.country.log.usecases.`in`.CountryCode
import io.github.country.log.usecases.`in`.LanguageCode
import io.github.country.log.usecases.out.CountryI18N
import io.github.country.log.usecases.services.FindCountryI18N

class FindCountryI18nFake : FindCountryI18N {
    private val inMemDb = mutableMapOf<Pair<CountryCode, LanguageCode>, String>()
        .apply {
            this[CountryCode("UA") to LanguageCode("EN")] = "Ukraine"
            this[CountryCode("UK") to LanguageCode("EN")] = "United Kingdom"
        }

    override fun find(countryCode: CountryCode, languageCode: LanguageCode): Option<CountryI18N> =
        inMemDb[countryCode to languageCode]
            ?.let { Option(CountryI18N(it)) }
            ?: None
}
