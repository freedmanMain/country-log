package io.github.country.log.usecases.fixtures

import io.github.country.log.usecases.repositories.LanguageRepository
import io.github.country.log.usecases.repositories.model.LanguageIsExists

class InMemLanguageRepository : LanguageRepository {
    private val inMemDb = mutableMapOf<LanguageIsExists, String>()
        .apply {
            this[LanguageIsExists("EN")] = """ { code: "EN" } """
            this[LanguageIsExists("UA")] = """ { code: "UA" } """
        }

    override fun isExists(value: LanguageIsExists): Boolean = inMemDb[value] != null
}