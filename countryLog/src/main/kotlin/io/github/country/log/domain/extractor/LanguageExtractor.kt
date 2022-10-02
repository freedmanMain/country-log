package io.github.country.log.domain.extractor

import arrow.core.Option
import io.github.country.log.domain.extractor.model.DestinationLanguageCode
import io.github.country.log.domain.model.LanguageCode

public interface LanguageExtractor {
    public fun extract(dst: DestinationLanguageCode): Option<LanguageCode>
}
