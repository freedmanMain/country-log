package io.github.country.log.domain.extractor

import io.github.country.log.domain.fixtures.InMemCountryExtractor
import io.github.country.log.domain.extractor.model.DestinationCountryCode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue

internal class CountryCodeExtractorTests : BehaviorSpec({
    given("representation of country code from destination source") {
        `when`("passed data was found in data source") {
            then("should return country code") {
                val dst = DestinationCountryCode("UA")
                val extractor: CountryExtractor = InMemCountryExtractor()
                extractor.extract(dst).all { it.asString() == "UA" }.shouldBeTrue()
            }
        }

        `when`("passed data was not found in data source") {
            then("should return nothing") {
                val dst = DestinationCountryCode("RU")
                val extractor: CountryExtractor = InMemCountryExtractor()
                extractor.extract(dst).isEmpty().shouldBeTrue()
            }
        }
    }
})
