package io.github.country.log.usecases.repositories

import io.github.country.log.usecases.fixtures.InMemCountryCodeRepository
import io.github.country.log.usecases.repositories.model.CountryCodeIsExists
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class CountryCodeRepositoryTests : BehaviorSpec({
    given("I want to check that country code is exists in repository") {
        `when`("I passed existent country code") {
            then("I should get true value") {
                val value = CountryCodeIsExists("UA")
                val repo: CountryCodeRepository = InMemCountryCodeRepository()

                repo.isExists(value).shouldBeTrue()
            }
        }

        `when`("I passed non existent country code") {
            then("I should get false value") {
                val value = CountryCodeIsExists("RU")
                val repo: CountryCodeRepository = InMemCountryCodeRepository()

                repo.isExists(value).shouldBeFalse()
            }
        }
    }
})
