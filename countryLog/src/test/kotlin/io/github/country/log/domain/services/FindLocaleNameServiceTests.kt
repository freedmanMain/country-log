package io.github.country.log.domain.services

import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.fixtures.InMemCountryExtractor
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue

internal class FindLocaleNameServiceTests : BehaviorSpec({
    given("language and country codes") {
        `when`("locale name was found") {
            then("should return data") {
                val language = LanguageCode("EN")
                val country = CountryCode("UA")

                val findLocaleName: FindLocaleName = FindLocaleNameService(InMemCountryExtractor())
                val receivedData = findLocaleName.find(country, language)

                receivedData.all { it.asString() == "Ukraine" }.shouldBeTrue()
            }
        }

        `when`("locale name was not found") {
            then("should return nothing") {
                val language = LanguageCode("EN")
                val country = CountryCode("RU")

                val findLocaleName: FindLocaleName = FindLocaleNameService(InMemCountryExtractor())

                findLocaleName.find(country, language).isEmpty().shouldBeTrue()
            }
        }
    }
})
