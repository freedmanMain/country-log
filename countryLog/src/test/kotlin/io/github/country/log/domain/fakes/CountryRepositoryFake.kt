package io.github.country.log.domain.fakes

import io.github.country.log.domain.model.CountryRepository

internal class CountryRepositoryFake : CountryRepository {
    private val inMemDb = mutableMapOf<String, String>()
        .apply {
            this["UA"] = """ { code: "UA" } """
            this["EN"] = """ { code: "EN" } """
        }

    override fun alreadyExists(value: String): Boolean = inMemDb[value] != null
}
