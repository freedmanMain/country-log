package io.github.country.log.domain.services

import io.github.country.log.domain.fixtures.InMemLanguageExtractor
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

internal class LanguageAlreadyExistsServiceTests : BehaviorSpec({
    given("data that represents language code") {
        `when`("passed data was found") {
            then("should return true") {
                val request = "EN"
                val languageAlreadyExists: LanguageAlreadyExists = LanguageAlreadyExistsService(InMemLanguageExtractor())

                languageAlreadyExists.check(request).shouldBeTrue()
            }
        }

        `when`("passed data was not found") {
            then("should return false") {
                val request = "RU"
                val languageAlreadyExists: LanguageAlreadyExists = LanguageAlreadyExistsService(InMemLanguageExtractor())

                languageAlreadyExists.check(request).shouldBeFalse()
            }
        }
    }
})
