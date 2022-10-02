package io.github.country.log.domain.services

import io.github.country.log.domain.extractor.CountryCodeExtractor
import io.github.country.log.domain.extractor.model.DestinationCountryCode
import io.github.country.log.domain.model.CountryCodeRequest

public interface CountryAlreadyExists {
    public fun check(request: CountryCodeRequest): Boolean
}

public class CountryAlreadyExistsService(
    private val extractor: CountryCodeExtractor
) : CountryAlreadyExists {

    public override fun check(request: CountryCodeRequest): Boolean =
        extractor.isNotUnknown(request.toDestination())

    private fun CountryCodeRequest.toDestination(): DestinationCountryCode = DestinationCountryCode(this.value)
}
