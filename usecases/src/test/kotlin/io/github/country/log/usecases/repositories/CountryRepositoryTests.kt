package io.github.country.log.usecases.repositories

import io.github.country.log.usecases.`in`.CountryCode
import io.github.country.log.usecases.`in`.LanguageCode
import io.github.country.log.usecases.fixtures.InMemCountryRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

class CountryRepositoryTests : BehaviorSpec({
    given("I want to find internalization name for country in repository") {
        `when`("The i18n name existent by language code and country code") {
            then("I should get i18n name for country") {
                val languageCode = LanguageCode("EN")
                val countryCode = CountryCode("UA")

                val repo: CountryRepository = InMemCountryRepository()

                val i18n = repo.findI18N(countryCode, languageCode)

                i18n.shouldNotBeNull()

                i18n shouldBe "Ukraine"
            }
        }

        `when`("The i18n name non existent by language code and country code") {
            then("I should get nothing(null)") {
                val languageCode = LanguageCode("EN")
                val countryCode = CountryCode("RU")

                val repo: CountryRepository = InMemCountryRepository()

                val i18n = repo.findI18N(countryCode, languageCode)

                i18n.shouldBeNull()
            }
        }
    }
})
