package io.github.country.log.domain.model.rules

import arrow.core.Either
import arrow.core.Nel
import io.github.country.log.domain.fakes.CountryRepositoryFake
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.CountryRepository
import io.github.country.log.domain.model.errors.CountryCodeCreationErrors
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class CountryCodeRulesTests : BehaviorSpec({
    given("data that represents country code") {
        `when`("validate country code with valid data") {
            then("should be success") {
                val request = "UA"
                val repository: CountryRepository = CountryRepositoryFake()

                val result = CountryCodeRules(repository, request)

                result.all { it.asString() == "UA" }.shouldBeTrue()
            }
        }

        `when`("validate country code with blank value") {
            then("should be failure") {
                val request = ""
                val repository: CountryRepository = CountryRepositoryFake()

                val result = CountryCodeRules(repository, request)

                result.shouldBeInstanceOf<Either.Left<CountryCodeCreationErrors.BlankCountryCodeError>>()
            }
        }

        `when`("validate country code input with non existent value") {
            then("should be failure") {
                val value = "RU"
                val exists: CountryRepository = CountryRepositoryFake()

                val result = CountryCodeRules(exists, value)

                result.shouldBeInstanceOf<Either.Left<CountryCodeCreationErrors.CountryCodeNotExistsError>>()
            }
        }
    }

    given("request that represents list of country codes") {
        `when`("validate country codes with valid data using 'FastFail' strategy") {
            then("should be success") {
                val requestList = listOf("UA", "EN")
                val repository: CountryRepository = CountryRepositoryFake()

                val result = CountryCodeRules(ValidationStrategy.FailFast, repository, requestList)

                result.shouldBeInstanceOf<Either.Right<List<CountryCode>>>()
                result.value.shouldBe(listOf(CountryCode("UA"), CountryCode("EN")))
            }
        }

        `when`("validate country codes with blank values using 'FailFast' strategy") {
            then("should be failure") {
                val requestList = listOf("", " ")
                val repository: CountryRepository = CountryRepositoryFake()

                val result = CountryCodeRules(ValidationStrategy.FailFast, repository, requestList)

                result.shouldBeInstanceOf<Either.Left<Nel<CountryCodeCreationErrors.BlankCountryCodeError>>>()
                result.value.size shouldBe 1
            }
        }

        `when`("validate country codes with unknown values using 'FailFast' strategy") {
            then("should be failure") {
                val requestList = listOf("RU", "NC")
                val repository: CountryRepository = CountryRepositoryFake()

                val result = CountryCodeRules(ValidationStrategy.FailFast, repository, requestList)

                result.shouldBeInstanceOf<Either.Left<Nel<CountryCodeCreationErrors.CountryCodeNotExistsError>>>()
                result.value.size shouldBe 1
            }
        }

        `when`("validate country codes with valid data using 'ErrorAccumulation' strategy") {
            then("should be success") {
                val requestList = listOf("UA", "EN")
                val repository: CountryRepository = CountryRepositoryFake()

                val result = CountryCodeRules(ValidationStrategy.ErrorAccumulation, repository, requestList)

                result.shouldBeInstanceOf<Either.Right<List<CountryCode>>>()
                result.value shouldBe (listOf(CountryCode("UA"), CountryCode("EN")))
            }
        }

        `when`("validate country codes with blank values using 'ErrorAccumulation' strategy") {
            then("should be failure") {
                val requestList = listOf("", " ")
                val repository: CountryRepository = CountryRepositoryFake()

                val result = CountryCodeRules(ValidationStrategy.ErrorAccumulation, repository, requestList)

                result.shouldBeInstanceOf<Either.Left<Nel<CountryCodeCreationErrors.NotAtCountryCodeError>>>()
                result.value.size shouldBe 2
            }
        }

        `when`("validate country codes with unknown values using 'ErrorAccumulation' strategy") {
            then("should be failure") {
                val requestList = listOf("RU", "NC")
                val repository: CountryRepository = CountryRepositoryFake()

                val result = CountryCodeRules(ValidationStrategy.ErrorAccumulation, repository, requestList)

                result.shouldBeInstanceOf<Either.Left<Nel<CountryCodeCreationErrors.NotAtCountryCodeError>>>()
                result.value.size shouldBe 2
            }
        }
    }
})
