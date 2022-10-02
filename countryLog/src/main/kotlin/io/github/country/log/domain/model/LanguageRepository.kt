package io.github.country.log.domain.model

interface LanguageRepository {
    fun alreadyExists(value: String): Boolean
}
