package io.github.country.log.domain.services

import io.github.country.log.domain.fixtures.InMemCountryExtractor
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

internal class CountryAlreadyExistsServiceTests : BehaviorSpec({
    given("data that represents language code") {
        `when`("passed data was found") {
            then("should return true") {
                val request = "UA"
                val countryAlreadyExists: CountryAlreadyExists = CountryAlreadyExistsService(InMemCountryExtractor())

                countryAlreadyExists.check(request).shouldBeTrue()
            }
        }

        `when`("passed data was not found") {
            then("should return false") {
                val request = "RU"
                val countryAlreadyExists: CountryAlreadyExists = CountryAlreadyExistsService(InMemCountryExtractor())

                countryAlreadyExists.check(request).shouldBeFalse()
            }
        }
    }
})
