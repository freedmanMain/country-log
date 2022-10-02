package io.github.country.log.inmem.persistence

import io.github.country.log.domain.model.CountryRepository

class InMemCountryRepository : CountryRepository{
    private val storage = mutableMapOf<String, String>()
        .apply {
            this["UA"] = """ { code: "UA" } """
            this["UK"] = """ { code: "UK" } """
            this["MD"] = """ { code: "MD" } """
        }

    override fun alreadyExists(value: String): Boolean = storage[value] != null
}
