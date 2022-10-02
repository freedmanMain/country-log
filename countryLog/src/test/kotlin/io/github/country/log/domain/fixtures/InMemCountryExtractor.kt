package io.github.country.log.domain.fixtures

import arrow.core.Option
import arrow.core.toOption
import io.github.country.log.domain.extractor.CountryExtractor
import io.github.country.log.domain.extractor.model.DestinationCountryCode
import io.github.country.log.domain.model.CountryCode

internal class InMemCountryExtractor : CountryExtractor {
    private val inMemDb = mutableMapOf<DestinationCountryCode, CountryCode>()
        .apply {
            this[DestinationCountryCode("UA")] = CountryCode("UA")
            this[DestinationCountryCode("UK")] = CountryCode("UK")
        }

    override fun extract(dst: DestinationCountryCode): Option<CountryCode> = inMemDb[dst].toOption()
}