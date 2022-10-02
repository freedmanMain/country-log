package io.github.country.log.domain.model

import arrow.core.Either
import io.github.country.log.domain.model.errors.CountryCodeCreationErrors
import io.github.country.log.domain.model.rules.CountryCodeRules
import io.github.country.log.domain.services.CountryAlreadyExists

@JvmInline
public value class CountryCode internal constructor(private val value: String) {

    public companion object {
        public fun make(
            data: String,
            countryAlreadyExists: CountryAlreadyExists
        ): Either<CountryCodeCreationErrors, CountryCode> = CountryCodeRules(countryAlreadyExists, data)
    }

    public fun asString(): String = value
}
