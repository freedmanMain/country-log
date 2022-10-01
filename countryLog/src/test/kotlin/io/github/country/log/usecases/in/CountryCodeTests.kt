package io.github.country.log.usecases.`in`

import arrow.core.Either
import io.github.country.log.usecases.`in`.rules.CountryCodeErrors
import io.github.country.log.usecases.fixtures.CountryCodeAlreadyExistsFake
import io.github.country.log.usecases.services.CountryCodeAlreadyExists
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue

class CountryCodeTests : BehaviorSpec({
    given("I have some value that may be a country code") {
        `when`("I make country code with valid data") {
            then("The country code creation should be successful") {
                val value = CountryCodeInput("UA")
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result = CountryCode.of(value = value, isExists = isExists)

                result.isRight().shouldBeTrue()
            }
        }

        `when`("I make country code with blank code value") {
            then("The country code creation should be failure") {
                val value = CountryCodeInput("")
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result =
                    CountryCode.of(value = value, isExists = isExists)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val error = result.value

                (error is CountryCodeErrors.EmptyCountryCode).shouldBeTrue()
            }
        }

        `when`("I make country with non existent value") {
            then("The country code creation should be failure") {
                val value = CountryCodeInput("RU")
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result =
                    CountryCode.of(value = value, isExists = isExists)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val error = result.value

                (error is CountryCodeErrors.CountryCodeNotExists).shouldBeTrue()
            }
        }
    }
})
