package io.github.country.log.usecases.services

import arrow.core.None
import arrow.core.Option
import io.github.country.log.usecases.`in`.CountryCode
import io.github.country.log.usecases.`in`.LanguageCode
import io.github.country.log.usecases.out.CountryI18N
import io.github.country.log.usecases.repositories.CountryRepository

class FindCountryI18nService(
    private val countryRepository: CountryRepository
) : FindCountryI18N {

    override fun find(countryCode: CountryCode, languageCode: LanguageCode): Option<CountryI18N> =
        countryRepository.findI18N(countryCode, languageCode)
            ?.let { Option(it.toI18n()) }
            ?: None

    private fun String.toI18n(): CountryI18N = CountryI18N(this)
}
