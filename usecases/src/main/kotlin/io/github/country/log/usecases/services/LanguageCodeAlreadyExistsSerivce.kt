package io.github.country.log.usecases.services

import io.github.country.log.usecases.`in`.LanguageCodeInput
import io.github.country.log.usecases.repositories.LanguageRepository
import io.github.country.log.usecases.repositories.model.LanguageIsExists

class LanguageCodeAlreadyExistsSerivce(
    private val languageRepository: LanguageRepository
) : LanguageCodeAlreadyExists {

    override fun check(languageCodeInput: LanguageCodeInput): Boolean =
        languageRepository.isExists(languageCodeInput.map())

    private fun LanguageCodeInput.map(): LanguageIsExists = LanguageIsExists(this.value)
}
