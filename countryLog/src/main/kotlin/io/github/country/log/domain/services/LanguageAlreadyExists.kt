package io.github.country.log.domain.services

import io.github.country.log.domain.extractor.LanguageExtractor
import io.github.country.log.domain.extractor.model.DestinationLanguageCode

public interface LanguageAlreadyExists {
    public fun check(data: String): Boolean
}

public class LanguageAlreadyExistsService(
    private val extractor: LanguageExtractor
) : LanguageAlreadyExists {

    public override fun check(data: String): Boolean =
        extractor.isNotUnknown(data.toDestination())

    private fun String.toDestination(): DestinationLanguageCode = DestinationLanguageCode(this)
}

