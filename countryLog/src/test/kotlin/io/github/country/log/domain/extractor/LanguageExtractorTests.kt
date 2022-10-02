package io.github.country.log.domain.extractor

import io.github.country.log.domain.fixtures.InMemLanguageExtractor
import io.github.country.log.domain.extractor.model.DestinationLanguageCode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

internal class LanguageExtractorTests : BehaviorSpec({
    given("representation of language code from destination source") {
        `when`("passed data was found in data source") {
            then("should return true") {
                val dst = DestinationLanguageCode("EN")
                val extractor: LanguageExtractor = InMemLanguageExtractor()

                extractor.isNotUnknown(dst).shouldBeTrue()
            }
        }

        `when`("passed data was not found in data source") {
            then("should return false") {
                val dst = DestinationLanguageCode("RU")
                val extractor: LanguageExtractor = InMemLanguageExtractor()

                extractor.isNotUnknown(dst).shouldBeFalse()
            }
        }
    }
})
