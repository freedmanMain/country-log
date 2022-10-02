package io.github.country.log.domain.extractor.model

@JvmInline
public value class DestinationCountryCode(private val value: String) {
    public fun asString(): String = value
}
