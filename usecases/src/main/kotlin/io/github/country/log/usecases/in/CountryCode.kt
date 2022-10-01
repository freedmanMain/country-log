package io.github.country.log.usecases.`in`

import arrow.core.Either
import arrow.core.Nel
import io.github.country.log.usecases.`in`.validation.CountryCodeErrors
import io.github.country.log.usecases.`in`.validation.CountryCodeRules
import io.github.country.log.usecases.`in`.validation.ValidationStrategy
import io.github.country.log.usecases.service.CountryCodeAlreadyExists

@JvmInline
value class CountryFormField(val value: String)

@JvmInline
value class CountryCode internal constructor(private val value: String) {

    companion object {
        fun of(
            value: CountryFormField,
            isExists: CountryCodeAlreadyExists
        ): Either<CountryCodeErrors, CountryCode> = CountryCodeRules(isExists, value)
    }

    fun code(): String = value
}

