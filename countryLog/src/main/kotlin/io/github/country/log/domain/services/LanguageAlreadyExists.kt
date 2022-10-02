package io.github.country.log.domain.services

import io.github.country.log.domain.extractor.LanguageExtractor
import io.github.country.log.domain.extractor.model.DestinationLanguageCode
import io.github.country.log.domain.model.LanguageCodeRequest

public interface LanguageAlreadyExists {
    public fun check(request: LanguageCodeRequest): Boolean
}

public class LanguageAlreadyExistsService(
    private val extractor: LanguageExtractor
) : LanguageAlreadyExists {

    public override fun check(request: LanguageCodeRequest): Boolean =
        extractor.isNotUnknown(request.toDestination())

    private fun LanguageCodeRequest.toDestination(): DestinationLanguageCode = DestinationLanguageCode(this.value)
}

