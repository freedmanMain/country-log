package io.github.country.log.usecases.`in`.validation

import arrow.core.Either
import io.github.country.log.usecases.`in`.InputCountryField
import io.github.country.log.usecases.fixtures.CountryCodeAlreadyExistsFake
import io.github.country.log.usecases.service.CountryCodeAlreadyExists
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class CountryCodeRulesTests : BehaviorSpec({
    given("I have some value that may be a country code") {
        `when`("I validate country code input with valid data") {
            then("Validation should be successful") {
                val value = InputCountryField("UA")
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result = CountryCodeRules(isExists, value)

                result.isRight().shouldBeTrue()
            }
        }

        `when`("I validate country code input with blank value") {
            then("Validation should be failure") {
                val value = InputCountryField("")
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
                val value = InputCountryField("RU")
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
                val fields = listOf(InputCountryField("UA"), InputCountryField("EN"))
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result = CountryCodeRules(ValidationStrategy.FailFast, isExists, fields)

                result.isRight().shouldBeTrue()
            }
        }

        `when`("I validate country codes input with blank values using 'failFast' strategy") {
            then("Validation should be failure") {
                val fields = listOf(InputCountryField(""), InputCountryField(""))
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result = CountryCodeRules(ValidationStrategy.FailFast, isExists, fields)

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
                val fields = listOf(InputCountryField("RU"), InputCountryField("NC"))
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result = CountryCodeRules(ValidationStrategy.FailFast, isExists, fields)

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
                val fields = listOf(InputCountryField(""), InputCountryField(""))
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result = CountryCodeRules(ValidationStrategy.ErrorAccumulation, isExists, fields)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val errors = result.value

                errors.size shouldBe 2

                errors.all {  it is CountryCodeErrors.NotAnCountryCode}.shouldBeTrue()
            }
        }

        `when`("I validate country codes input with non existent values using 'errorAccumulation' strategy") {
            then("Validation should be failure") {
                val fields = listOf(InputCountryField("RU"), InputCountryField("NC"))
                val isExists: CountryCodeAlreadyExists = CountryCodeAlreadyExistsFake()

                val result = CountryCodeRules(ValidationStrategy.ErrorAccumulation, isExists, fields)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val errors = result.value

                errors.size shouldBe 2

                errors.all {  it is CountryCodeErrors.NotAnCountryCode}.shouldBeTrue()
            }
        }
    }
})
