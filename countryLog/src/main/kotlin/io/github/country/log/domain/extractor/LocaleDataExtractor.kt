package io.github.country.log.domain.extractor

import arrow.core.Option
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.services.result.LocaleName

public interface LocaleDataExtractor {
    public fun extract(country: CountryCode, language: LanguageCode): Option<LocaleName>
}
