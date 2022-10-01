package io.github.country.log.usecases.repositories

import io.github.country.log.usecases.`in`.CountryCode
import io.github.country.log.usecases.`in`.LanguageCode

interface CountryRepository {
    fun findI18N(countryCode: CountryCode, languageCode: LanguageCode): String?
}
