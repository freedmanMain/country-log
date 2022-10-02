package io.github.country.log.domain.model

import io.github.country.log.domain.fakes.CountryRepositoryFake
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

internal class LanguageRepositoryTests : BehaviorSpec({
    given("string representation of language code") {
        `when`("passed data already exists in datasource") {
            then("should return true") {
                val value = "EN"
                val repository: CountryRepository = CountryRepositoryFake()
                repository.alreadyExists(value).shouldBeTrue()
            }
        }

        `when`("passed data was not exists in datasource") {
            then("should return false") {
                val value = "RU"
                val repository: CountryRepository = CountryRepositoryFake()
                repository.alreadyExists(value).shouldBeFalse()
            }
        }
    }
})
