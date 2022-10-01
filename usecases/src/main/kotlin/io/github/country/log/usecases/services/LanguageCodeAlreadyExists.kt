package io.github.country.log.usecases.services

import io.github.country.log.usecases.`in`.LanguageCodeInput

interface LanguageCodeAlreadyExists {
    fun check(languageCodeInput: LanguageCodeInput): Boolean
}
