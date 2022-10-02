package io.github.country.log.domain.model.errors

import io.github.country.log.domain.model.base.Error

public sealed class LanguageCodeCreationErrors : Error {
    public object BlankLanguageCodeError : LanguageCodeCreationErrors()
    public object LanguageCodeNotExistsError : LanguageCodeCreationErrors()
    public object NotAtLanguageError : LanguageCodeCreationErrors()
}
