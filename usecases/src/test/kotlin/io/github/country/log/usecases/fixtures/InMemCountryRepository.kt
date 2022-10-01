package io.github.country.log.usecases.fixtures

import io.github.country.log.usecases.`in`.CountryCode
import io.github.country.log.usecases.`in`.LanguageCode
import io.github.country.log.usecases.repositories.CountryRepository

class InMemCountryRepository : CountryRepository {
    private val inMemDb = mutableMapOf<Pair<CountryCode, LanguageCode>, String>()
        .apply {
            this[CountryCode("UA") to LanguageCode("EN")] = "Ukraine"
            this[CountryCode("UK") to LanguageCode("EN")] = "United Kingdom"
        }

    override fun findI18N(countryCode: CountryCode, languageCode: LanguageCode): String? =
        inMemDb[countryCode to languageCode]
}
