package io.github.country.log.usecases.services

import arrow.core.Option
import io.github.country.log.usecases.`in`.CountryCode
import io.github.country.log.usecases.`in`.LanguageCode
import io.github.country.log.usecases.out.CountryI18N

interface FindCountryI18N {
    fun find(countryCode: CountryCode, languageCode: LanguageCode): Option<CountryI18N>
}
