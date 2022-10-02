package io.github.country.log.domain.services

import io.github.country.log.domain.extractor.CountryExtractor
import io.github.country.log.domain.extractor.model.DestinationCountryCode

public interface CountryAlreadyExists {
    public fun check(data: String): Boolean
}

public class CountryAlreadyExistsService(
    private val extractor: CountryExtractor
) : CountryAlreadyExists {

    public override fun check(data: String): Boolean =
        extractor.extract(data.toDestination()).isNotEmpty()

    private fun String.toDestination(): DestinationCountryCode = DestinationCountryCode(this)
}
