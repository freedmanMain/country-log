package io.github.country.log.domain.extractor

import io.github.country.log.domain.extractor.model.DestinationCountryCode

public interface CountryCodeExtractor {
    public fun isNotUnknown(dst: DestinationCountryCode): Boolean
}
