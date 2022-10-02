package io.github.country.log.domain.fixtures

import io.github.country.log.domain.services.CountryAlreadyExists

internal class CountryAlreadyExistsFake : CountryAlreadyExists {
    private val inMemDb = mutableMapOf<String, String>()
        .apply {
            this["UA"] = """ { code: "UA" } """
            this["EN"] = """ { code: "EN" } """
        }

    override fun check(data: String): Boolean = inMemDb[data] != null
}
