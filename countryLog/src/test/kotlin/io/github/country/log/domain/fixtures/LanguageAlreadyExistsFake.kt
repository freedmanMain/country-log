package io.github.country.log.domain.fixtures

import io.github.country.log.domain.services.LanguageAlreadyExists

class LanguageAlreadyExistsFake : LanguageAlreadyExists {
    private val inMemDb = mutableMapOf<String, String>()
        .apply {
            this["EN"] = """ { code: "UA" } """
            this["UA"] = """ { code: "EN" } """
        }

    override fun check(data: String): Boolean = inMemDb[data] != null
}
