package io.github.country.log.domain.extractor

import io.github.country.log.domain.fixtures.InMemLocaleDataExtractor
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue

internal class CountryExtractorTests : BehaviorSpec({
    given("representation of country and language codes from destination source ") {
        `when`("locale name was found in data source") {
            then("should return locale name") {
                val language = LanguageCode("EN")
                val country = CountryCode("UA")
                val extractor: LocaleDataExtractor = InMemLocaleDataExtractor()

                val locale = extractor.extract(country, language)
                locale.all { it.asString() == "Ukraine" }.shouldBeTrue()
            }
        }

        `when`("locale name was not found in data source") {
            then("should return nothing") {
                val language = LanguageCode("EN")
                val country = CountryCode("RU")
                val extractor: LocaleDataExtractor = InMemLocaleDataExtractor()

                extractor.extract(country, language).isEmpty().shouldBeTrue()
            }
        }
    }
})
