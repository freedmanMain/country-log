package io.github.country.log.usecases.service

import io.github.country.log.usecases.`in`.LanguageCode

interface LanguageCodeAlreadyExists {
    fun check(languageCode: LanguageCode): Boolean
}
