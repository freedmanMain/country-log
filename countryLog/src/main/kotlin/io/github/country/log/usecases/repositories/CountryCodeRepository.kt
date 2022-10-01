package io.github.country.log.usecases.repositories

import io.github.country.log.usecases.repositories.model.CountryCodeIsExists

interface CountryCodeRepository {
    fun isExists(value: CountryCodeIsExists): Boolean
}
