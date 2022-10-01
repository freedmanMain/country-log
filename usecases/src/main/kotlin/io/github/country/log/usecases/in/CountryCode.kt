package io.github.country.log.usecases.`in`

import arrow.core.Either
import io.github.country.log.usecases.`in`.rules.CountryCodeErrors
import io.github.country.log.usecases.`in`.rules.CountryCodeRules
import io.github.country.log.usecases.services.CountryCodeAlreadyExists

@JvmInline
value class CountryCodeInput(val value: String)

@JvmInline
value class CountryCode internal constructor(private val value: String) {

    companion object {
        fun of(
            value: CountryCodeInput,
            isExists: CountryCodeAlreadyExists
        ): Either<CountryCodeErrors, CountryCode> = CountryCodeRules(isExists, value)
    }

    fun code(): String = value
}
