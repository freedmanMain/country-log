package io.github.country.log.domain.model

import arrow.core.Either
import io.github.country.log.domain.fakes.CountryRepositoryFake
import io.github.country.log.domain.model.errors.CountryCodeCreationErrors
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.types.shouldBeInstanceOf

internal class CountryCodeTests : BehaviorSpec({
    given("data that represents country code") {
        `when`("make country code with valid data") {
            then("country code creation should be successful") {
                val data = "UA"
                val repository: CountryRepository = CountryRepositoryFake()

                val result = CountryCode.make(data, repository)

                result.all { it.asString() == "UA" }.shouldBeTrue()
            }
        }

        `when`("make country code with blank code value") {
            then("country code creation should be failure") {
                val data = ""
                val repository: CountryRepository = CountryRepositoryFake()

                val result = CountryCode.make(data, repository)

                result.shouldBeInstanceOf<Either.Left<CountryCodeCreationErrors.BlankCountryCodeError>>()
            }
        }

        `when`("make country with unknown value") {
            then("country code creation should be failure") {
                val request = "RU"
                val repository: CountryRepository = CountryRepositoryFake()

                val result = CountryCode.make(request, repository)

                result.shouldBeInstanceOf<Either.Left<CountryCodeCreationErrors.CountryCodeNotExistsError>>()
            }
        }
    }
})
