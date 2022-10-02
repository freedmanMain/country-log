package io.github.country.log.domain.model

import arrow.core.Either
import io.github.country.log.domain.model.errors.LanguageCodeCreationErrors
import io.github.country.log.domain.model.rules.LanguageCodeRules
import io.github.country.log.domain.services.LanguageAlreadyExists

@JvmInline
public value class LanguageCode internal constructor(private val value: String) {

    public companion object {
        public fun make(
            data: String,
            languageAlreadyExists: LanguageAlreadyExists
        ): Either<LanguageCodeCreationErrors, LanguageCode> = LanguageCodeRules(languageAlreadyExists, data)
    }

    public fun asString(): String = value
}
