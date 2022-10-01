package io.github.country.log.usecases.repositories

import io.github.country.log.usecases.fixtures.InMemLanguageRepository
import io.github.country.log.usecases.repositories.model.LanguageIsExists
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class LanguageRepositoryTests : BehaviorSpec({
    given("I want to check that language code is exists in repository") {
        `when`("I passed existent language code") {
            then("I should get true value") {
                val value = LanguageIsExists("EN")
                val repo: LanguageRepository = InMemLanguageRepository()

                repo.isExists(value).shouldBeTrue()
            }
        }

        `when`("I passed non existent language code") {
            then("I should get false value") {
                val value = LanguageIsExists("RU")
                val repo: LanguageRepository = InMemLanguageRepository()

                repo.isExists(value).shouldBeFalse()
            }
        }
    }
})
