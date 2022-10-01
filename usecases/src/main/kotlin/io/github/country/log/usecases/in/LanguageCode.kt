package io.github.country.log.usecases.`in`

import arrow.core.Either
import io.github.country.log.usecases.`in`.validation.LanguageCodeErrors
import io.github.country.log.usecases.`in`.validation.LanguageCodeRules
import io.github.country.log.usecases.service.LanguageCodeAlreadyExists

@JvmInline
value class LanguageCodeInput(val value: String)

@JvmInline
value class LanguageCode internal constructor(private val value: String) {

    companion object {
        fun of(
            value: LanguageCodeInput,
            isExists: LanguageCodeAlreadyExists
        ): Either<LanguageCodeErrors, LanguageCode> = LanguageCodeRules(value, isExists)
    }

    fun code(): String = value
}
