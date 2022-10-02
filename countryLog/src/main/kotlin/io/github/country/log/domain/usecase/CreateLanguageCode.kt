package io.github.country.log.domain.usecase

import arrow.core.Either
import io.github.country.log.domain.model.LanguageCode

public interface CreateLanguageCode {
    public fun execute(value: String): Either<CreateLanguageCodeUseCaseErrors, LanguageCode>
}

public sealed class CreateLanguageCodeUseCaseErrors {
    public abstract val message: String

    public data class EmptyLanguageCode(override val message: String) : CreateLanguageCodeUseCaseErrors()
    public data class UnknownLanguageCode(override val message: String) : CreateLanguageCodeUseCaseErrors()
}