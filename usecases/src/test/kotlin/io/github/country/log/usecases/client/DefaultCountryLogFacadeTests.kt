package io.github.country.log.usecases.client

import arrow.core.Either
import io.github.country.log.usecases.`in`.CountryCode
import io.github.country.log.usecases.`in`.CountryCodeInput
import io.github.country.log.usecases.`in`.LanguageCode
import io.github.country.log.usecases.`in`.LanguageCodeInput
import io.github.country.log.usecases.`in`.rules.CountryCodeErrors
import io.github.country.log.usecases.`in`.rules.LanguageCodeErrors
import io.github.country.log.usecases.fixtures.CountryCodeAlreadyExistsFake
import io.github.country.log.usecases.fixtures.FindCountryI18nFake
import io.github.country.log.usecases.fixtures.LanguageCodeAlreadyExistsFake
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class DefaultCountryLogFacadeTests : BehaviorSpec({
    given("Client provides input for country code") {
        `when`("Client provides valid data") {
            then("Client should receive valid country code") {

                val client: CountryLogClient = DefaultCountryLogClient(
                    countryCodeIsExists = CountryCodeAlreadyExistsFake(),
                    languageCodeIsExists = LanguageCodeAlreadyExistsFake(),
                    findCountryI18n = FindCountryI18nFake()
                )

                val countryInput = CountryCodeInput("UA")

                val result = client.createCountryCode(countryInput)

                result.isRight().shouldBeTrue()
            }
        }

        `when`("Client provides invalid data") {
            then("Client should receive error") {

                val client: CountryLogClient = DefaultCountryLogClient(
                    countryCodeIsExists = CountryCodeAlreadyExistsFake(),
                    languageCodeIsExists = LanguageCodeAlreadyExistsFake(),
                    findCountryI18n = FindCountryI18nFake()
                )

                client.createCountryCode(CountryCodeInput(""))
                    .shouldBeInstanceOf<Either.Left<CountryCodeErrors.EmptyCountryCode>>()

                client.createCountryCode(CountryCodeInput("RU"))
                    .shouldBeInstanceOf<Either.Left<CountryCodeErrors.CountryCodeNotExists>>()
            }
        }
    }

    given("Client provides input for language code") {
        `when`("Client provides valid data") {
            then("Client should receive valid language code") {

                val client: CountryLogClient = DefaultCountryLogClient(
                    countryCodeIsExists = CountryCodeAlreadyExistsFake(),
                    languageCodeIsExists = LanguageCodeAlreadyExistsFake(),
                    findCountryI18n = FindCountryI18nFake()
                )

                val input = LanguageCodeInput("UA")

                val result = client.createLanguageCode(input)

                result.isRight().shouldBeTrue()
            }
        }

        `when`("Client provides invalid data") {
            then("Client should receive error") {

                val client: CountryLogClient = DefaultCountryLogClient(
                    countryCodeIsExists = CountryCodeAlreadyExistsFake(),
                    languageCodeIsExists = LanguageCodeAlreadyExistsFake(),
                    findCountryI18n = FindCountryI18nFake()
                )

                client.createLanguageCode(LanguageCodeInput(""))
                    .shouldBeInstanceOf<Either.Left<LanguageCodeErrors.EmptyLanguageCode>>()

                client.createLanguageCode(LanguageCodeInput("RU"))
                    .shouldBeInstanceOf<Either.Left<LanguageCodeErrors.LanguageCodeNotExists>>()
            }
        }
    }

    given("Client provides language code and country code in order to get i18n name for country") {
        `when`("i18n name existent in system") {
            then("Client should receive i18n name for country") {

                val client: CountryLogClient = DefaultCountryLogClient(
                    countryCodeIsExists = CountryCodeAlreadyExistsFake(),
                    languageCodeIsExists = LanguageCodeAlreadyExistsFake(),
                    findCountryI18n = FindCountryI18nFake()
                )

                val languageCode = LanguageCode("EN")
                val countryCode = CountryCode("UA")

                val result = client.findI18nForCountry(countryCode, languageCode)

                result.isRight().shouldBeTrue()

                result as Either.Right

                result.value.i18n() shouldBe "Ukraine"
            }
        }

        `when`("i18n name non existent in system") {
            then("Client should receive error") {

                val client: CountryLogClient = DefaultCountryLogClient(
                    countryCodeIsExists = CountryCodeAlreadyExistsFake(),
                    languageCodeIsExists = LanguageCodeAlreadyExistsFake(),
                    findCountryI18n = FindCountryI18nFake()
                )

                val languageCode = LanguageCode("EN")
                val countryCode = CountryCode("RU")

                val result = client.findI18nForCountry(countryCode, languageCode)

                result.shouldBeInstanceOf<Either.Left<I18nForCountryNotFound>>()
            }
        }
    }
})
