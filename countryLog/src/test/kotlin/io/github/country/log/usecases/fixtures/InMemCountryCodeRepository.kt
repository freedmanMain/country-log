package io.github.country.log.usecases.fixtures

import io.github.country.log.usecases.repositories.CountryCodeRepository
import io.github.country.log.usecases.repositories.model.CountryCodeIsExists

class InMemCountryCodeRepository : CountryCodeRepository {
    private val inMemDb = mutableMapOf<CountryCodeIsExists, String>()
        .apply {
            this[CountryCodeIsExists("UA")] = """ { code: "UA" } """
            this[CountryCodeIsExists("UK")] = """ { code: "UK" } """
        }

    override fun isExists(value: CountryCodeIsExists): Boolean = inMemDb[value] != null
}