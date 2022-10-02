package io.github.country.log.domain.usecase

import arrow.core.Either
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.model.errors.CountryNameNotFound
import io.github.country.log.domain.access.extraction.CountryName

public interface GetCountryName {
    public fun execute(
        country: CountryCode,
        language: LanguageCode
    ): Either<CountryNameNotFound, CountryName>
}
