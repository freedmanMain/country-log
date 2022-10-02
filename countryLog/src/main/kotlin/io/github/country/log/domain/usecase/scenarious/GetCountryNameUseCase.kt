package io.github.country.log.domain.usecase.scenarious

import arrow.core.Either
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.model.errors.CountryNameNotFound
import io.github.country.log.domain.usecase.GetCountryName
import io.github.country.log.domain.access.LocaleDataExtractor
import io.github.country.log.domain.access.extraction.CountryName

public class GetCountryNameUseCase(
    private val extractor: LocaleDataExtractor
) : GetCountryName {

    public override fun execute(
        country: CountryCode,
        language: LanguageCode
    ): Either<CountryNameNotFound, CountryName> =
        extractor.extract(country, language).toEither { CountryNameNotFound }
}
