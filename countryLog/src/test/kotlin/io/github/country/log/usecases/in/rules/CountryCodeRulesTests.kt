package io.github.country.log.usecases.`in`.rules

import arrow.core.Either
import io.github.country.log.usecases.`in`.CountryCodeInput
import io.github.country.log.usecases.fixtures.CountryCodeAlreadyExistsFake
import io.github.country.log.usecases.services.CountryCodeAlreadyExists
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class CountryCodeRulesTests : BehaviorSpec({
    given("I have some value that may be a country code") {
        `when`("I validate country code input with valid data") {
            then("Validation should be successful") {
                val value = CountryCodeInput("UA")
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result = CountryCodeRules(isExists, value)

                result.isRight().shouldBeTrue()
            }
        }

        `when`("I validate country code input with blank value") {
            then("Validation should be failure") {
                val value = CountryCodeInput("")
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result = CountryCodeRules(isExists, value)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val error = result.value

                (error is CountryCodeErrors.EmptyCountryCode).shouldBeTrue()
            }
        }

        `when`("I validate country code input with non existent value") {
            then("Validation should be failure") {
                val value = CountryCodeInput("RU")
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result = CountryCodeRules(isExists, value)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val error = result.value

                (error is CountryCodeErrors.CountryCodeNotExists).shouldBeTrue()
            }
        }
    }

    given("I have some values that may be a country codes") {
        `when`("I validate valid country codes input using 'fastFail' strategy") {
            then("Validation should be successful") {
                val fields = listOf(CountryCodeInput("UA"), CountryCodeInput("EN"))
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result = CountryCodeRules(Strategy.FailFast, isExists, fields)

                result.isRight().shouldBeTrue()
            }
        }

        `when`("I validate country codes input with blank values using 'failFast' strategy") {
            then("Validation should be failure") {
                val fields = listOf(CountryCodeInput(""), CountryCodeInput(""))
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result = CountryCodeRules(Strategy.FailFast, isExists, fields)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val errors = result.value

                errors.size shouldBe 1

                val error = errors.first()

                (error is CountryCodeErrors.EmptyCountryCode).shouldBeTrue()
            }
        }

        `when`("I validate country codes input with non existent values using 'failFast' strategy") {
            then("Validation should be failure") {
                val fields = listOf(CountryCodeInput("RU"), CountryCodeInput("NC"))
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result = CountryCodeRules(Strategy.FailFast, isExists, fields)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val errors = result.value

                errors.size shouldBe 1

                val error = errors.first()

                (error is CountryCodeErrors.CountryCodeNotExists).shouldBeTrue()
            }
        }

        `when`("I validate country codes input with blank values using 'errorAccumulation' strategy") {
            then("Validation should be failure") {
                val fields = listOf(CountryCodeInput(""), CountryCodeInput(""))
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result = CountryCodeRules(Strategy.ErrorAccumulation, isExists, fields)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val errors = result.value

                errors.size shouldBe 2

                errors.all {  it is CountryCodeErrors.NotAnCountryCode}.shouldBeTrue()
            }
        }

        `when`("I validate country codes input with non existent values using 'errorAccumulation' strategy") {
            then("Validation should be failure") {
                val fields = listOf(CountryCodeInput("RU"), CountryCodeInput("NC"))
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result = CountryCodeRules(Strategy.ErrorAccumulation, isExists, fields)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val errors = result.value

                errors.size shouldBe 2

                errors.all {  it is CountryCodeErrors.NotAnCountryCode}.shouldBeTrue()
            }
        }
    }
})
