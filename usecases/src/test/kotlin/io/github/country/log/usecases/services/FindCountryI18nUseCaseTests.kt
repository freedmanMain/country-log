package io.github.country.log.usecases.services

import io.github.country.log.usecases.`in`.CountryCode
import io.github.country.log.usecases.`in`.LanguageCode
import io.github.country.log.usecases.fixtures.InMemCountryRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue

class FindCountryI18nUseCaseTests : BehaviorSpec({
    given("I want to find internalization name of country by language and code") {
        `when`("Internalization for country code and language code is exists") {
            then("I should get i18n name for country") {
                val languageCode = LanguageCode("EN")
                val countryCode = CountryCode("UA")

                val findCountryI18N: FindCountryI18N = FindCountryI18nUseCase(InMemCountryRepository())

                findCountryI18N.find(countryCode, languageCode).isNotEmpty().shouldBeTrue()
            }
        }

        `when`("Internalization for country code and language code is not exists") {
            then("I should get nothing") {
                val languageCode = LanguageCode("EN")
                val countryCode = CountryCode("RU")

                val findCountryI18N: FindCountryI18N = FindCountryI18nUseCase(InMemCountryRepository())

                findCountryI18N.find(countryCode, languageCode).isEmpty().shouldBeTrue()
            }
        }
    }
})
