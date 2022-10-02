package io.github.country.log.domain.fixtures

import io.github.country.log.domain.model.CountryCodeRequest
import io.github.country.log.domain.services.CountryAlreadyExists

class CountryAlreadyExistsFake : CountryAlreadyExists {
    private val inMemDb = mutableMapOf<CountryCodeRequest, String>()
        .apply {
            this[CountryCodeRequest("UA")] = """ { code: "UA" } """
            this[CountryCodeRequest("EN")] = """ { code: "EN" } """
        }

    override fun check(request: CountryCodeRequest): Boolean = inMemDb[request] != null
}
