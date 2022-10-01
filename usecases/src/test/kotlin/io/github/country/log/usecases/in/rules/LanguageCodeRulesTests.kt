package io.github.country.log.usecases.`in`.rules

import arrow.core.Either
import io.github.country.log.usecases.`in`.LanguageCodeInput
import io.github.country.log.usecases.fixtures.LanguageCodeAlreadyExistsFake
import io.github.country.log.usecases.services.LanguageCodeAlreadyExists
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class LanguageCodeRulesTests : BehaviorSpec({
    given("I have some value that may be a language code") {
        `when`("I validate language code input with valid data") {
            then("Validation should be successful") {
                val value = LanguageCodeInput("UA")
                val isExists: LanguageCodeAlreadyExists = LanguageCodeAlreadyExistsFake()

                val result = LanguageCodeRules(isExists, value)

                result.isRight().shouldBeTrue()
            }
        }

        `when`("I validate language code input with blank value") {
            then("Validation should be failure") {
                val value = LanguageCodeInput("")
                val isExists: LanguageCodeAlreadyExists = LanguageCodeAlreadyExistsFake()

                val result = LanguageCodeRules(isExists, value)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val error = result.value

                (error is LanguageCodeErrors.EmptyLanguageCode).shouldBeTrue()
            }
        }

        `when`("I validate language code input with non existent value") {
            then("Validation should be failure") {
                val value = LanguageCodeInput("RU")
                val isExists: LanguageCodeAlreadyExists = LanguageCodeAlreadyExistsFake()

                val result = LanguageCodeRules(isExists, value)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val error = result.value

                (error is LanguageCodeErrors.LanguageCodeNotExists).shouldBeTrue()
            }
        }
    }

    given("I have some values that may be a language codes") {
        `when`("I validate valid language codes input using 'fastFail' strategy") {
            then("Validation should be successful") {
                val fields = listOf(LanguageCodeInput("UA"), LanguageCodeInput("EN"))
                val isExists: LanguageCodeAlreadyExists = LanguageCodeAlreadyExistsFake()

                val result = LanguageCodeRules(Strategy.FailFast, isExists, fields)

                result.isRight().shouldBeTrue()
            }
        }

        `when`("I validate language codes input with blank values using 'failFast' strategy") {
            then("Validation should be failure") {
                val fields = listOf(LanguageCodeInput(""), LanguageCodeInput(""))
                val isExists: LanguageCodeAlreadyExists = LanguageCodeAlreadyExistsFake()

                val result = LanguageCodeRules(Strategy.FailFast, isExists, fields)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val errors = result.value

                errors.size shouldBe 1

                val error = errors.first()

                (error is LanguageCodeErrors.EmptyLanguageCode).shouldBeTrue()
            }
        }

        `when`("I validate language codes input with non existent values using 'failFast' strategy") {
            then("Validation should be failure") {
                val fields = listOf(LanguageCodeInput("RU"), LanguageCodeInput("NC"))
                val isExists: LanguageCodeAlreadyExists = LanguageCodeAlreadyExistsFake()

                val result = LanguageCodeRules(Strategy.FailFast, isExists, fields)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val errors = result.value

                errors.size shouldBe 1

                val error = errors.first()

                (error is LanguageCodeErrors.LanguageCodeNotExists).shouldBeTrue()
            }
        }

        `when`("I validate language codes input with blank values using 'errorAccumulation' strategy") {
            then("Validation should be failure") {
                val fields = listOf(LanguageCodeInput(""), LanguageCodeInput(""))
                val isExists: LanguageCodeAlreadyExists = LanguageCodeAlreadyExistsFake()

                val result = LanguageCodeRules(Strategy.ErrorAccumulation, isExists, fields)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val errors = result.value

                errors.size shouldBe 2

                errors.all { it is LanguageCodeErrors.NotAtLanguage }.shouldBeTrue()
            }
        }

        `when`("I validate language codes input with non existent values using 'errorAccumulation' strategy") {
            then("Validation should be failure") {
                val fields = listOf(LanguageCodeInput("RU"), LanguageCodeInput("NC"))
                val isExists: LanguageCodeAlreadyExists = LanguageCodeAlreadyExistsFake()

                val result = LanguageCodeRules(Strategy.ErrorAccumulation, isExists, fields)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val errors = result.value

                errors.size shouldBe 2

                errors.all { it is LanguageCodeErrors.NotAtLanguage }.shouldBeTrue()
            }
        }
    }
})