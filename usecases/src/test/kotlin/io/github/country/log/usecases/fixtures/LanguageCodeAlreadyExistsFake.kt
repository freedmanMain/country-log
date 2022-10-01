package io.github.country.log.usecases.fixtures

import io.github.country.log.usecases.`in`.LanguageCodeInput
import io.github.country.log.usecases.service.LanguageCodeAlreadyExists

class LanguageCodeAlreadyExistsFake : LanguageCodeAlreadyExists {
    private val inMemDb = mutableMapOf<LanguageCodeInput, String>()
        .apply {
            this[LanguageCodeInput("EN")] = """ { code: "UA" } """
            this[LanguageCodeInput("UA")] = """ { code: "EN" } """
        }

    override fun check(languageCodeInput: LanguageCodeInput): Boolean = inMemDb[languageCodeInput] != null
}
