package io.github.country.log.inmem.persistence

import io.github.country.log.domain.model.LanguageRepository

class InMemLanguageRepository : LanguageRepository {
    private val storage = mutableMapOf<String, String>()
        .apply {
            this["EN"] = """ { code: "UA" } """
            this["UA"] = """ { code: "EN" } """
            this["MD"] = """ { code: "MD" } """
        }


    override fun alreadyExists(value: String): Boolean = storage[value] != null
}
