package io.github.country.log.domain.services

import io.github.country.log.domain.model.CountryCodeRequest
import io.github.country.log.domain.fixtures.InMemCountryCodeExtractor
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class CountryAlreadyExistsServiceTests : BehaviorSpec({
    given("request that represents language code") {
        `when`("passed data was found") {
            then("should return true") {
                val request = CountryCodeRequest("UA")
                val countryAlreadyExists: CountryAlreadyExists = CountryAlreadyExistsService(InMemCountryCodeExtractor())

                countryAlreadyExists.check(request).shouldBeTrue()
            }
        }

        `when`("passed data was not found") {
            then("should return false") {
                val request = CountryCodeRequest("RU")
                val countryAlreadyExists: CountryAlreadyExists = CountryAlreadyExistsService(InMemCountryCodeExtractor())

                countryAlreadyExists.check(request).shouldBeFalse()
            }
        }
    }
})
