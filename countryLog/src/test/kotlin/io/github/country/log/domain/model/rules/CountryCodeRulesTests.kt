package io.github.country.log.domain.model.rules

import arrow.core.Either
import arrow.core.Nel
import io.github.country.log.domain.model.CountryCodeRequest
import io.github.country.log.domain.fixtures.CountryAlreadyExistsFake
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.errors.CountryCodeCreationErrors
import io.github.country.log.domain.services.CountryAlreadyExists
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class CountryCodeRulesTests : BehaviorSpec({
    given("request that represents country code") {
        `when`("validate country code with valid data") {
            then("should be success") {
                val request = CountryCodeRequest("UA")
                val countryAlreadyExists: CountryAlreadyExists = CountryAlreadyExistsFake()

                val result = CountryCodeRules(countryAlreadyExists, request)

                result.all { it.asString() == "UA" }.shouldBeTrue()
            }
        }

        `when`("validate country code with blank value") {
            then("should be failure") {
                val request = CountryCodeRequest("")
                val countryAlreadyExists: CountryAlreadyExists = CountryAlreadyExistsFake()

                val result = CountryCodeRules(countryAlreadyExists, request)

                result.shouldBeInstanceOf<Either.Left<CountryCodeCreationErrors.BlankCountryCodeError>>()
            }
        }

        `when`("validate country code input with non existent value") {
            then("should be failure") {
                val value = CountryCodeRequest("RU")
                val exists: CountryAlreadyExists = CountryAlreadyExistsFake()

                val result = CountryCodeRules(exists, value)

                result.shouldBeInstanceOf<Either.Left<CountryCodeCreationErrors.CountryCodeNotExistsError>>()
            }
        }
    }

    given("request that represents list of country codes") {
        `when`("validate country codes with valid data using 'FastFail' strategy") {
            then("should be success") {
                val requestList = listOf(CountryCodeRequest("UA"), CountryCodeRequest("EN"))
                val countryAlreadyExists: CountryAlreadyExists = CountryAlreadyExistsFake()

                val result = CountryCodeRules(ValidationStrategy.FailFast, countryAlreadyExists, requestList)

                result.shouldBeInstanceOf<Either.Right<List<CountryCode>>>()
                result.value.shouldBe(listOf(CountryCode("UA"), CountryCode("EN")))
            }
        }

        `when`("validate country codes with blank values using 'FailFast' strategy") {
            then("should be failure") {
                val requestList = listOf(CountryCodeRequest(""), CountryCodeRequest(""))
                val countryAlreadyExists: CountryAlreadyExists = CountryAlreadyExistsFake()

                val result = CountryCodeRules(ValidationStrategy.FailFast, countryAlreadyExists, requestList)

                result.shouldBeInstanceOf<Either.Left<Nel<CountryCodeCreationErrors.BlankCountryCodeError>>>()
                result.value.size shouldBe 1
            }
        }

        `when`("validate country codes with unknown values using 'FailFast' strategy") {
            then("should be failure") {
                val requestList = listOf(CountryCodeRequest("RU"), CountryCodeRequest("NC"))
                val countryAlreadyExists: CountryAlreadyExists = CountryAlreadyExistsFake()

                val result = CountryCodeRules(ValidationStrategy.FailFast, countryAlreadyExists, requestList)

                result.shouldBeInstanceOf<Either.Left<Nel<CountryCodeCreationErrors.CountryCodeNotExistsError>>>()
                result.value.size shouldBe 1
            }
        }

        `when`("validate country codes with valid data using 'ErrorAccumulation' strategy") {
            then("should be success") {
                val requestList = listOf(CountryCodeRequest("UA"), CountryCodeRequest("EN"))
                val countryAlreadyExists: CountryAlreadyExists = CountryAlreadyExistsFake()

                val result = CountryCodeRules(ValidationStrategy.ErrorAccumulation, countryAlreadyExists, requestList)

                result.shouldBeInstanceOf<Either.Right<List<CountryCode>>>()
                result.value shouldBe (listOf(CountryCode("UA"), CountryCode("EN")))
            }
        }

        `when`("validate country codes with blank values using 'ErrorAccumulation' strategy") {
            then("should be failure") {
                val requestList = listOf(CountryCodeRequest(""), CountryCodeRequest(""))
                val countryAlreadyExists: CountryAlreadyExists = CountryAlreadyExistsFake()

                val result = CountryCodeRules(ValidationStrategy.ErrorAccumulation, countryAlreadyExists, requestList)

                result.shouldBeInstanceOf<Either.Left<Nel<CountryCodeCreationErrors.NotAtCountryCodeError>>>()
                result.value.size shouldBe 2
            }
        }

        `when`("validate country codes with unknown values using 'ErrorAccumulation' strategy") {
            then("should be failure") {
                val requestList = listOf(CountryCodeRequest("RU"), CountryCodeRequest("NC"))
                val countryAlreadyExists: CountryAlreadyExists = CountryAlreadyExistsFake()

                val result = CountryCodeRules(ValidationStrategy.ErrorAccumulation, countryAlreadyExists, requestList)

                result.shouldBeInstanceOf<Either.Left<Nel<CountryCodeCreationErrors.NotAtCountryCodeError>>>()
                result.value.size shouldBe 2
            }
        }
    }
})
