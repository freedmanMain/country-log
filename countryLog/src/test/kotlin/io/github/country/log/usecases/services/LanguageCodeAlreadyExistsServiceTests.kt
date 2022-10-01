package io.github.country.log.usecases.services

import io.github.country.log.usecases.`in`.LanguageCodeInput
import io.github.country.log.usecases.fixtures.InMemLanguageRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class LanguageCodeAlreadyExistsServiceTests : BehaviorSpec({
    given("I want to check that language code is exists") {
        `when`("I passed existent language code") {
            then("I should get true value") {
                val input = LanguageCodeInput("EN")
                val isExists: LanguageCodeAlreadyExists = LanguageCodeAlreadyExistsSerivce(InMemLanguageRepository())

                isExists.check(input).shouldBeTrue()
            }
        }

        `when`("I passed non existent language code") {
            then("I should get false value") {
                val input = LanguageCodeInput("RU")
                val isExists: LanguageCodeAlreadyExists = LanguageCodeAlreadyExistsSerivce(InMemLanguageRepository())

                isExists.check(input).shouldBeFalse()
            }
        }
    }
})
