package io.github.country.log.domain.fixtures

import io.github.country.log.domain.extractor.CountryCodeExtractor
import io.github.country.log.domain.extractor.model.DestinationCountryCode

class InMemCountryCodeExtractor : CountryCodeExtractor {
    private val inMemDb = mutableMapOf<DestinationCountryCode, String>()
        .apply {
            this[DestinationCountryCode("UA")] = """ { code: "UA" } """
            this[DestinationCountryCode("UK")] = """ { code: "UK" } """
        }

    override fun isNotUnknown(dst: DestinationCountryCode): Boolean = inMemDb[dst] != null
}