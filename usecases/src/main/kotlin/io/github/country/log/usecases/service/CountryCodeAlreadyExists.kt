package io.github.country.log.usecases.service

import io.github.country.log.usecases.`in`.CountryFormField

interface CountryCodeAlreadyExists {
    fun check(countryCode: CountryFormField): Boolean
}
