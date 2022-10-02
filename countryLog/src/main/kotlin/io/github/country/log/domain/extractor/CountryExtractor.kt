package io.github.country.log.domain.extractor

import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode

public interface CountryExtractor {
    public fun findLocaleName(country: CountryCode, language: LanguageCode): String?
}
