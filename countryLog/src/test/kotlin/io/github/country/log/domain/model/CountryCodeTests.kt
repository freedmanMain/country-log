package io.github.country.log.domain.model

import arrow.core.Either
import io.github.country.log.domain.fixtures.CountryAlreadyExistsFake
import io.github.country.log.domain.model.errors.CountryCodeCreationErrors
import io.github.country.log.domain.services.CountryAlreadyExists
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.types.shouldBeInstanceOf

internal class CountryCodeTests : BehaviorSpec({
    given("data that represents country code") {
        `when`("make country code with valid data") {
            then("country code creation should be successful") {
                val data = "UA"
                val countryAlreadyExists: CountryAlreadyExists = CountryAlreadyExistsFake()

                val result = CountryCode.make(data, countryAlreadyExists)

                result.all { it.asString() == "UA" }.shouldBeTrue()
            }
        }

        `when`("make country code with blank code value") {
            then("country code creation should be failure") {
                val data = ""
                val countryAlreadyExists: CountryAlreadyExists = CountryAlreadyExistsFake()

                val result = CountryCode.make(data, countryAlreadyExists)

                result.shouldBeInstanceOf<Either.Left<CountryCodeCreationErrors.BlankCountryCodeError>>()
            }
        }

        `when`("make country with unknown value") {
            then("country code creation should be failure") {
                val request = "RU"
                val countryAlreadyExists: CountryAlreadyExists = CountryAlreadyExistsFake()

                val result = CountryCode.make(request, countryAlreadyExists)

                result.shouldBeInstanceOf<Either.Left<CountryCodeCreationErrors.CountryCodeNotExistsError>>()
            }
        }
    }
})
