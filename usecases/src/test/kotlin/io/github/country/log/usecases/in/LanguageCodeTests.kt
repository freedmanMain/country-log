package io.github.country.log.usecases.`in`

import arrow.core.Either
import io.github.country.log.usecases.`in`.rules.LanguageCodeErrors
import io.github.country.log.usecases.fixtures.LanguageCodeAlreadyExistsFake
import io.github.country.log.usecases.services.LanguageCodeAlreadyExists
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue

class LanguageCodeTests : BehaviorSpec({
    given("I have some value that may be a language code") {
        `when`("I make language code with valid data") {
            then("The language code creation should be successful") {
                val value = LanguageCodeInput("UA")
                val isExists: LanguageCodeAlreadyExists = LanguageCodeAlreadyExistsFake()

                val result = LanguageCode.of(value, isExists)

                result.isRight().shouldBeTrue()
            }
        }

        `when`("I make language code with blank value") {
            then("The language code creation should be failure") {
                val value = LanguageCodeInput("")
                val isExists: LanguageCodeAlreadyExists = LanguageCodeAlreadyExistsFake()

                val result = LanguageCode.of(value, isExists)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val error = result.value

                (error is LanguageCodeErrors.EmptyLanguageCode).shouldBeTrue()
            }
        }

        `when`("I make language code with non existent value") {
            then("The language code creation should be failure") {
                val value = LanguageCodeInput("RU")
                val isExists: LanguageCodeAlreadyExists = LanguageCodeAlreadyExistsFake()

                val result = LanguageCode.of(value, isExists)

                result.isLeft().shouldBeTrue()

                result as Either.Left

                val error = result.value

                (error is LanguageCodeErrors.LanguageCodeNotExists).shouldBeTrue()
            }
        }
    }
})
