package io.github.country.log.domain.model.rules

import arrow.core.Either
import arrow.core.Nel
import io.github.country.log.domain.model.LanguageCodeRequest
import io.github.country.log.domain.fixtures.LanguageAlreadyExistsFake
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.model.errors.LanguageCodeCreationErrors
import io.github.country.log.domain.services.LanguageAlreadyExists
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class LanguageCodeRulesTests : BehaviorSpec({
    given("request that represents language code") {
        `when`("validate with valid data") {
            then("should be success") {
                val request = LanguageCodeRequest("UA")
                val languageAlreadyExists: LanguageAlreadyExists = LanguageAlreadyExistsFake()

                val result = LanguageCodeRules(languageAlreadyExists, request)

                result.all { it.asString() == "UA" }.shouldBeTrue()
            }
        }

        `when`("validate language code with blank value") {
            then("should be failure") {
                val request = LanguageCodeRequest("")
                val languageAlreadyExists: LanguageAlreadyExists = LanguageAlreadyExistsFake()

                val result = LanguageCodeRules(languageAlreadyExists, request)

                result.shouldBeInstanceOf<Either.Left<LanguageCodeCreationErrors.BlankLanguageCodeError>>()
            }
        }

        `when`("validate language code with unknown value") {
            then("should be failure") {
                val request = LanguageCodeRequest("RU")
                val languageAlreadyExists: LanguageAlreadyExists = LanguageAlreadyExistsFake()

                val result = LanguageCodeRules(languageAlreadyExists, request)

                result.shouldBeInstanceOf<Either.Left<LanguageCodeCreationErrors.LanguageCodeNotExistsError>>()
            }
        }
    }

    given("request that represents list of language codes") {
        `when`("validate language codes with valid data using 'FastFail' strategy") {
            then("should be success") {
                val requestList = listOf(LanguageCodeRequest("UA"), LanguageCodeRequest("EN"))
                val languageAlreadyExists: LanguageAlreadyExists = LanguageAlreadyExistsFake()

                val result = LanguageCodeRules(ValidationStrategy.FailFast, languageAlreadyExists, requestList)

                result.shouldBeInstanceOf<Either.Right<List<LanguageCode>>>()
                result.value.shouldBe(listOf(LanguageCode("UA"), LanguageCode("EN")))
            }
        }

        `when`("validate language codes with blank values using 'FailFast' strategy") {
            then("should be failure") {
                val requestList = listOf(LanguageCodeRequest(""), LanguageCodeRequest(""))
                val languageAlreadyExists: LanguageAlreadyExists = LanguageAlreadyExistsFake()

                val result = LanguageCodeRules(ValidationStrategy.FailFast, languageAlreadyExists, requestList)

                result.shouldBeInstanceOf<Either.Left<Nel<LanguageCodeCreationErrors.BlankLanguageCodeError>>>()
                result.value.size shouldBe 1
            }
        }

        `when`("validate language codes with unknown values using 'FailFast' strategy") {
            then("should be failure") {
                val requestList = listOf(LanguageCodeRequest("RU"), LanguageCodeRequest("NC"))
                val languageAlreadyExists: LanguageAlreadyExists = LanguageAlreadyExistsFake()

                val result = LanguageCodeRules(ValidationStrategy.FailFast, languageAlreadyExists, requestList)

                result.shouldBeInstanceOf<Either.Left<Nel<LanguageCodeCreationErrors.LanguageCodeNotExistsError>>>()
                result.value.size shouldBe 1
            }
        }

        `when`("validate language codes with valid data using 'ErrorAccumulation' strategy") {
            then("should be success") {
                val requestList = listOf(LanguageCodeRequest("UA"), LanguageCodeRequest("EN"))
                val languageAlreadyExists: LanguageAlreadyExists = LanguageAlreadyExistsFake()

                val result = LanguageCodeRules(ValidationStrategy.ErrorAccumulation, languageAlreadyExists, requestList)

                result.shouldBeInstanceOf<Either.Right<List<LanguageCode>>>()
                result.value.shouldBe(listOf(LanguageCode("UA"), LanguageCode("EN")))
            }
        }

        `when`("validate language codes with blank values using 'ErrorAccumulation' strategy") {
            then("should be failure") {
                val requestList = listOf(LanguageCodeRequest(""), LanguageCodeRequest(""))
                val languageAlreadyExists: LanguageAlreadyExists = LanguageAlreadyExistsFake()

                val result = LanguageCodeRules(ValidationStrategy.ErrorAccumulation, languageAlreadyExists, requestList)

                result.shouldBeInstanceOf<Either.Left<Nel<LanguageCodeCreationErrors.NotAtLanguageError>>>()
                result.value.size shouldBe 2
            }
        }

        `when`("validate language codes with unknown values using 'ErrorAccumulation' strategy") {
            then("should be failure") {
                val requestList = listOf(LanguageCodeRequest("RU"), LanguageCodeRequest("NC"))
                val languageAlreadyExists: LanguageAlreadyExists = LanguageAlreadyExistsFake()

                val result = LanguageCodeRules(ValidationStrategy.ErrorAccumulation, languageAlreadyExists, requestList)

                result.shouldBeInstanceOf<Either.Left<Nel<LanguageCodeCreationErrors.NotAtLanguageError>>>()
                result.value.size shouldBe 2
            }
        }
    }
})
