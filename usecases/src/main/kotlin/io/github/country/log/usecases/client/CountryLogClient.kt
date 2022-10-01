package io.github.country.log.usecases.client

import arrow.core.Either
import io.github.country.log.usecases.`in`.CountryCode
import io.github.country.log.usecases.`in`.CountryCodeInput
import io.github.country.log.usecases.`in`.LanguageCode
import io.github.country.log.usecases.`in`.LanguageCodeInput
import io.github.country.log.usecases.`in`.rules.CountryCodeErrors
import io.github.country.log.usecases.`in`.rules.LanguageCodeErrors
import io.github.country.log.usecases.common.UseCaseError
import io.github.country.log.usecases.out.CountryI18N

interface CountryLogClient {
    fun createCountryCode(countryCodeInput: CountryCodeInput): Either<CountryCodeErrors, CountryCode>

    fun createLanguageCode(languageCodeInput: LanguageCodeInput): Either<LanguageCodeErrors, LanguageCode>

    fun findI18nForCountry(
        countryCode: CountryCode,
        languageCode: LanguageCode
    ): Either<I18nForCountryNotFound, CountryI18N>
}

object I18nForCountryNotFound : UseCaseError
