package io.github.country.log.domain.client

import arrow.core.Either
import io.github.country.log.domain.fixtures.ClientFake
import io.github.country.log.domain.fixtures.InMemLocaleDataExtractor
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.model.errors.LocaleNameNotFoundError
import io.github.country.log.domain.services.result.LocaleName
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class ClientTests : BehaviorSpec({
    given("client has country and language codes") {
        `when`("locale name was found") {
            then("client should receive data") {
                val language = LanguageCode("EN")
                val country = CountryCode("UA")
                val client: Client = ClientFake(InMemLocaleDataExtractor())

                val result = client.provideLocaleName(country, language)
                result.shouldBeInstanceOf<Either.Right<LocaleName>>()
                result.value.asString() shouldBe "Ukraine"
            }
        }

        `when`("locale name was not found") {
            then("client should receive error") {
                val client: Client = ClientFake(InMemLocaleDataExtractor())
                val languageCode = LanguageCode("EN")
                val countryCode = CountryCode("RU")

                val result = client.provideLocaleName(countryCode, languageCode)
                result.shouldBeInstanceOf<Either.Left<LocaleNameNotFoundError>>()
            }
        }
    }
})
