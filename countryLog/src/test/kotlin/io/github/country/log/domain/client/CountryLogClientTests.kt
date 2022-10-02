package io.github.country.log.domain.client

import arrow.core.Either
import io.github.country.log.domain.fixtures.CountryLogClientFake
import io.github.country.log.domain.fixtures.FindLocaleNameFake
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.model.errors.LocaleNameNotFoundError
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class CountryLogClientTests : BehaviorSpec({
    given("client has country and language codes") {
        `when`("locale name was found") {
            then("client should receive data") {
                val language = LanguageCode("EN")
                val country = CountryCode("UA")
                val client: CountryLogClient = CountryLogClientFake(FindLocaleNameFake())

                val result = client.findLocaleName(country, language)
                result.isRight().shouldBeTrue()
                result as Either.Right
                result.value.asString() shouldBe "Ukraine"
            }
        }

        `when`("locale name was not found") {
            then("client should receive error") {
                val client: CountryLogClient = CountryLogClientFake(FindLocaleNameFake())
                val languageCode = LanguageCode("EN")
                val countryCode = CountryCode("RU")

                val result = client.findLocaleName(countryCode, languageCode)
                result.shouldBeInstanceOf<Either.Left<LocaleNameNotFoundError>>()
            }
        }
    }
})
