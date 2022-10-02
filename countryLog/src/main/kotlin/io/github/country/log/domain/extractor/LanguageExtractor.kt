package io.github.country.log.domain.extractor

import io.github.country.log.domain.extractor.model.DestinationLanguageCode

public interface LanguageExtractor {
    public fun isNotUnknown(dst: DestinationLanguageCode): Boolean
}
