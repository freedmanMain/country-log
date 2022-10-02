package io.github.country.log.domain.model

interface CountryRepository {
    fun alreadyExists(value: String): Boolean
}
