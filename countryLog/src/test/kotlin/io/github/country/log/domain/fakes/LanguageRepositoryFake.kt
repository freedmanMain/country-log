package io.github.country.log.domain.fakes

import io.github.country.log.domain.model.LanguageRepository

class LanguageRepositoryFake : LanguageRepository {
    private val inMemDb = mutableMapOf<String, String>()
        .apply {
            this["EN"] = """ { code: "UA" } """
            this["UA"] = """ { code: "EN" } """
        }

    override fun alreadyExists(value: String): Boolean = inMemDb[value] != null
}
