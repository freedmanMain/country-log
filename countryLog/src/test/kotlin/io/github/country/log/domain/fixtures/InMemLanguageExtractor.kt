package io.github.country.log.domain.fixtures

import io.github.country.log.domain.extractor.LanguageExtractor
import io.github.country.log.domain.extractor.model.DestinationLanguageCode

internal class InMemLanguageExtractor : LanguageExtractor {
    private val inMemDb = mutableMapOf<DestinationLanguageCode, String>()
        .apply {
            this[DestinationLanguageCode("EN")] = """ { code: "EN" } """
            this[DestinationLanguageCode("UA")] = """ { code: "UA" } """
        }

    override fun isNotUnknown(dst: DestinationLanguageCode): Boolean = inMemDb[dst] != null
}
