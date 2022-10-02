package io.github.country.log.domain.model

import arrow.core.Either
import io.github.country.log.domain.fixtures.LanguageAlreadyExistsFake
import io.github.country.log.domain.model.errors.LanguageCodeCreationErrors
import io.github.country.log.domain.services.LanguageAlreadyExists
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.types.shouldBeInstanceOf

internal class LanguageCodeTests : BehaviorSpec({
    given("request that represents language code") {
        `when`("make language code with valid data") {
            then("language code creation should be successful") {
                val request = LanguageCodeRequest("UA")
                val languageAlreadyExists: LanguageAlreadyExists = LanguageAlreadyExistsFake()

                val result = LanguageCode.make(request, languageAlreadyExists)

                result.all { it.asString() == "UA" }.shouldBeTrue()
            }
        }

        `when`("make language code with blank value") {
            then("language code creation should be failure") {
                val request = LanguageCodeRequest("")
                val languageAlreadyExists: LanguageAlreadyExists = LanguageAlreadyExistsFake()

                val result = LanguageCode.make(request, languageAlreadyExists)

                result.shouldBeInstanceOf<Either.Left<LanguageCodeCreationErrors.BlankLanguageCodeError>>()
            }
        }

        `when`("make language code with unknown value") {
            then("language code creation should be failure") {
                val request = LanguageCodeRequest("RU")
                val languageAlreadyExists: LanguageAlreadyExists = LanguageAlreadyExistsFake()

                val result = LanguageCode.make(request, languageAlreadyExists)

                result.shouldBeInstanceOf<Either.Left<LanguageCodeCreationErrors.LanguageCodeNotExistsError>>()
            }
        }
    }
})
