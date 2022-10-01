package io.github.country.log.usecases.services

import arrow.core.Option
import com.sun.imageio.plugins.common.I18N
import io.github.country.log.usecases.`in`.CountryCode
import io.github.country.log.usecases.`in`.LanguageCode

interface FindCountryI18N {
    fun find(countryCode: CountryCode, languageCode: LanguageCode): Option<I18N>
}
