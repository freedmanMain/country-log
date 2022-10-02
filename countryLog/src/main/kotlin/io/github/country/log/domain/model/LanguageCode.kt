package io.github.country.log.domain.model

import arrow.core.Either
import io.github.country.log.domain.model.errors.LanguageCodeCreationErrors
import io.github.country.log.domain.model.rules.LanguageCodeRules

@JvmInline
public value class LanguageCode internal constructor(private val value: String) {

    public companion object {
        public fun make(
            data: String,
            repository: LanguageRepository
        ): Either<LanguageCodeCreationErrors, LanguageCode> = LanguageCodeRules(repository, data)
    }

    public fun asString(): String = value
}
