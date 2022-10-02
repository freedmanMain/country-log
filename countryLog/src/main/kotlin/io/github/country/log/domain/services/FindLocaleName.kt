package io.github.country.log.domain.services

import arrow.core.None
import arrow.core.Option
import io.github.country.log.domain.extractor.CountryExtractor
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.services.result.LocaleName

public interface FindLocaleName {
    public fun find(country: CountryCode, language: LanguageCode): Option<LocaleName>
}

public class FindLocaleNameService(
    private val extractor: CountryExtractor
) : FindLocaleName {

    public override fun find(country: CountryCode, language: LanguageCode): Option<LocaleName> =
        extractor.findLocaleName(country, language)
            ?.let { Option(it.toLocaleName()) }
            ?: None

    private fun String.toLocaleName(): LocaleName = LocaleName(this)
}
