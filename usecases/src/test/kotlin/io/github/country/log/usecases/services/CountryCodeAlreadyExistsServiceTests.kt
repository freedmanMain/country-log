package io.github.country.log.usecases.services

import io.github.country.log.usecases.`in`.CountryCodeInput
import io.github.country.log.usecases.fixtures.InMemCountryCodeRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class CountryCodeAlreadyExistsServiceTests : BehaviorSpec({
    given("I want to check that country code is exists") {
        `when`("I passed existent language code") {
            then("I should get true value") {
                val input = CountryCodeInput("UA")
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsService(InMemCountryCodeRepository())

                isExists.check(input).shouldBeTrue()
            }
        }

        `when`("I passed non existent language code") {
            then("I should get false value") {
                val input = CountryCodeInput("RU")
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsService(InMemCountryCodeRepository())

                isExists.check(input).shouldBeFalse()
            }
        }
    }
})
