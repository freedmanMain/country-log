package io.github.country.log.domain.model.errors

import io.github.country.log.domain.model.base.Error

public sealed class CountryCodeCreationErrors : Error {
    public object BlankCountryCodeError : CountryCodeCreationErrors()
    public object CountryCodeNotExistsError : CountryCodeCreationErrors()
    public object NotAtCountryCodeError : CountryCodeCreationErrors()
}
