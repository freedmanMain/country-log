package io.github.country.log.usecases.repositories.model

@JvmInline
value class CountryCodeIsExists(private val value: String) {
    fun asString(): String = value
}
