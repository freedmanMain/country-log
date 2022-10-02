package io.github.country.log.domain.access

import io.github.country.log.domain.fakes.LocaleDataExtractorFake
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.access.LocaleDataExtractor
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue

internal class LocaleDateExtractorTests : BehaviorSpec({
    given("representation of country and language codes from destination source ") {
        `when`("locale name was found in data source") {
            then("should return locale name") {
                val language = LanguageCode("EN")
                val country = CountryCode("UA")
                val extractor: LocaleDataExtractor = LocaleDataExtractorFake()

                val locale = extractor.extract(country, language)
                locale.all { it.asString() == "Ukraine" }.shouldBeTrue()
            }
        }

        `when`("locale name was not found in data source") {
            then("should return nothing") {
                val language = LanguageCode("EN")
                val country = CountryCode("RU")
                val extractor: LocaleDataExtractor = LocaleDataExtractorFake()

                extractor.extract(country, language).isEmpty().shouldBeTrue()
            }
        }
    }
})
