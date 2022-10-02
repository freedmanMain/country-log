package io.github.country.log.domain.access

import arrow.core.Option
import io.github.country.log.domain.access.extraction.CountryName
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode

public interface LocaleDataExtractor {
    public fun extract(country: CountryCode, language: LanguageCode): Option<CountryName>
}
