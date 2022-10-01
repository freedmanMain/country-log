package io.github.country.log.usecases.client

import arrow.core.Either
import io.github.country.log.usecases.`in`.CountryCode
import io.github.country.log.usecases.`in`.CountryCodeInput
import io.github.country.log.usecases.`in`.LanguageCode
import io.github.country.log.usecases.`in`.LanguageCodeInput
import io.github.country.log.usecases.`in`.rules.CountryCodeErrors
import io.github.country.log.usecases.`in`.rules.LanguageCodeErrors
import io.github.country.log.usecases.out.CountryI18N
import io.github.country.log.usecases.services.CountryCodeAlreadyExists
import io.github.country.log.usecases.services.FindCountryI18N
import io.github.country.log.usecases.services.LanguageCodeAlreadyExists

class DefaultCountryLogClient(
    private val countryCodeIsExists: CountryCodeAlreadyExists,
    private val languageCodeIsExists: LanguageCodeAlreadyExists,
    private val findCountryI18n: FindCountryI18N
) : CountryLogClient {

    override fun createCountryCode(countryCodeInput: CountryCodeInput): Either<CountryCodeErrors, CountryCode> =
        CountryCode.of(countryCodeInput, countryCodeIsExists)

    override fun createLanguageCode(languageCodeInput: LanguageCodeInput): Either<LanguageCodeErrors, LanguageCode> =
        LanguageCode.of(languageCodeInput, languageCodeIsExists)

    override fun findI18nForCountry(
        countryCode: CountryCode,
        languageCode: LanguageCode
    ): Either<I18nForCountryNotFound, CountryI18N> =
        findCountryI18n.find(countryCode, languageCode)
            .toEither { I18nForCountryNotFound }
}
