package io.github.country.log.domain.fixtures

import arrow.core.Option
import arrow.core.toOption
import io.github.country.log.domain.extractor.LanguageExtractor
import io.github.country.log.domain.extractor.model.DestinationLanguageCode
import io.github.country.log.domain.model.LanguageCode

internal class InMemLanguageExtractor : LanguageExtractor {
    private val inMemDb = mutableMapOf<DestinationLanguageCode, LanguageCode>()
        .apply {
            this[DestinationLanguageCode("EN")] = LanguageCode("EN")
            this[DestinationLanguageCode("UA")] = LanguageCode("UA")
        }

    override fun extract(dst: DestinationLanguageCode): Option<LanguageCode> = inMemDb[dst].toOption()
}
