package io.github.country.log.domain.extractor

import arrow.core.Option
import io.github.country.log.domain.extractor.model.DestinationCountryCode
import io.github.country.log.domain.model.CountryCode

public interface CountryExtractor {
    public fun extract(dst: DestinationCountryCode): Option<CountryCode>
}
