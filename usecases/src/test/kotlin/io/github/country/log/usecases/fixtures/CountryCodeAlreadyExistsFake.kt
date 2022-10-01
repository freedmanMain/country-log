package io.github.country.log.usecases.fixtures

import io.github.country.log.usecases.`in`.CountryFormField
import io.github.country.log.usecases.service.CountryCodeAlreadyExists

class CountryCodeAlreadyExistsFake : CountryCodeAlreadyExists {
    private val inMemDb = mutableMapOf<CountryFormField, String>()
        .apply {
            this[CountryFormField("UA")] = """ { code: "UA" } """
        }

    override fun check(countryCode: CountryFormField): Boolean = inMemDb[countryCode] != null
}
