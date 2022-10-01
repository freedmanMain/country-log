package io.github.country.log.usecases.service

import io.github.country.log.usecases.`in`.InputCountryField

interface CountryCodeAlreadyExists {
    fun check(inputCountryCode: InputCountryField): Boolean
}
