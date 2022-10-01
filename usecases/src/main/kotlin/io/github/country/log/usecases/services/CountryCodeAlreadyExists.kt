package io.github.country.log.usecases.services

import io.github.country.log.usecases.`in`.CountryCodeInput

interface CountryCodeAlreadyExists {
    fun check(countryCode: CountryCodeInput): Boolean
}
