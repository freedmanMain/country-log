package io.github.country.log.domain.extractor

import io.github.country.log.domain.fixtures.InMemCountryExtractor
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

internal class CountryExtractorTests : BehaviorSpec({
    given("representation of country and language codes from destination source ") {
        `when`("locale name was found in data source") {
            then("should return data") {
                val language = LanguageCode("EN")
                val country = CountryCode("UA")
                val extractor: CountryExtractor = InMemCountryExtractor()

                val locale = extractor.findLocaleName(country, language)
                locale.shouldNotBeNull()
                locale shouldBe "Ukraine"
            }
        }

        `when`("locale name was not found in data source") {
            then("should return null") {
                val language = LanguageCode("EN")
                val country = CountryCode("RU")
                val extractor: CountryExtractor = InMemCountryExtractor()

                extractor.findLocaleName(country, language).shouldBeNull()
            }
        }
    }
})
