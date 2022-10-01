package io.github.country.log.usecases.fixtures

import io.github.country.log.usecases.`in`.CountryCodeInput
import io.github.country.log.usecases.services.CountryCodeAlreadyExists

class CountryCodeAlreadyExistsFake : CountryCodeAlreadyExists {
    private val inMemDb = mutableMapOf<CountryCodeInput, String>()
        .apply {
            this[CountryCodeInput("UA")] = """ { code: "UA" } """
            this[CountryCodeInput("EN")] = """ { code: "EN" } """
        }

    override fun check(countryCode: CountryCodeInput): Boolean = inMemDb[countryCode] != null
}
