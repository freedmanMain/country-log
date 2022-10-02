package io.github.country.log.domain.services

import io.github.country.log.domain.model.LanguageCodeRequest
import io.github.country.log.domain.fixtures.InMemLanguageExtractor
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class LanguageAlreadyExistsServiceTests : BehaviorSpec({
    given("request that represents language code") {
        `when`("passed data was found") {
            then("should return true") {
                val request = LanguageCodeRequest("EN")
                val languageAlreadyExists: LanguageAlreadyExists = LanguageAlreadyExistsService(InMemLanguageExtractor())

                languageAlreadyExists.check(request).shouldBeTrue()
            }
        }

        `when`("passed data was not found") {
            then("should return false") {
                val request = LanguageCodeRequest("RU")
                val languageAlreadyExists: LanguageAlreadyExists = LanguageAlreadyExistsService(InMemLanguageExtractor())

                languageAlreadyExists.check(request).shouldBeFalse()
            }
        }
    }
})
