package io.github.country.log.usecases.fixtures

import io.github.country.log.usecases.`in`.InputCountryField
import io.github.country.log.usecases.service.CountryCodeAlreadyExists

class CountryCodeAlreadyExistsFake : CountryCodeAlreadyExists {
    private val inMemDb = mutableMapOf<InputCountryField, String>()
        .apply {
            this[InputCountryField("UA")] = """ { code: "UA" } """
            this[InputCountryField("EN")] = """ { code: "EN" } """
        }

    override fun check(inputCountryCode: InputCountryField): Boolean = inMemDb[inputCountryCode] != null
}
