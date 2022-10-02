package io.github.country.log.domain.access.extraction

@JvmInline
public value class CountryName(private val value: String) {
    public fun asString(): String = value
}
