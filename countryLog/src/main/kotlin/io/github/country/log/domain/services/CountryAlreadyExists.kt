package io.github.country.log.domain.services

import io.github.country.log.domain.extractor.CountryCodeExtractor
import io.github.country.log.domain.extractor.model.DestinationCountryCode

public interface CountryAlreadyExists {
    public fun check(data: String): Boolean
}

public class CountryAlreadyExistsService(
    private val extractor: CountryCodeExtractor
) : CountryAlreadyExists {

    public override fun check(data: String): Boolean =
        extractor.isNotUnknown(data.toDestination())

    private fun String.toDestination(): DestinationCountryCode = DestinationCountryCode(this)
}
