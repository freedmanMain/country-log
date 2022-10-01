package io.github.country.log.usecases.repositories

import io.github.country.log.usecases.repositories.model.LanguageIsExists

interface LanguageRepository {
    fun isExists(value: LanguageIsExists): Boolean
}
