package io.github.country.log.usecases.services

import io.github.country.log.usecases.`in`.CountryCodeInput
import io.github.country.log.usecases.repositories.CountryCodeRepository
import io.github.country.log.usecases.repositories.model.CountryCodeIsExists

class CountryCodeAlreadyExistsService(
    private val countyCodeRepository: CountryCodeRepository
) : CountryCodeAlreadyExists {

    override fun check(countryCode: CountryCodeInput): Boolean =
        countyCodeRepository.isExists(countryCode.map())

    private fun CountryCodeInput.map(): CountryCodeIsExists = CountryCodeIsExists(this.value)
}
