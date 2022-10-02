package io.github.country.log.domain.extractor

import io.github.country.log.domain.fixtures.InMemCountryCodeExtractor
import io.github.country.log.domain.extractor.model.DestinationCountryCode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

internal class CountryCodeExtractorTests : BehaviorSpec({
    given("representation of country code from destination source") {
        `when`("passed data was found in data source") {
            then("should return true") {
                val dst = DestinationCountryCode("UA")
                val extractor: CountryCodeExtractor = InMemCountryCodeExtractor()
                extractor.isNotUnknown(dst).shouldBeTrue()
            }
        }

        `when`("passed data was not found in data source") {
            then("should return false") {
                val dst = DestinationCountryCode("RU")
                val extractor: CountryCodeExtractor = InMemCountryCodeExtractor()
                extractor.isNotUnknown(dst).shouldBeFalse()
            }
        }
    }
})
