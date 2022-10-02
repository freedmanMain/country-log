package io.github.country.log.domain.model

import arrow.core.Either
import io.github.country.log.domain.model.errors.CountryCodeCreationErrors
import io.github.country.log.domain.model.rules.CountryCodeRules

@JvmInline
public value class CountryCode internal constructor(private val value: String) {

    public companion object {
        public fun make(
            data: String,
            repository: CountryRepository
        ): Either<CountryCodeCreationErrors, CountryCode> = CountryCodeRules(repository, data)
    }

    public fun asString(): String = value
}
