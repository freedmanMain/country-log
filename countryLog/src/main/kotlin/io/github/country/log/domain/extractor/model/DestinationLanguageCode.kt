package io.github.country.log.domain.extractor.model

@JvmInline
public value class DestinationLanguageCode(private val value: String) {
    public fun asString(): String = value
}
