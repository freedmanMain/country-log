package io.github.country.log.domain.services.result

@JvmInline
public value class LocaleName(private val value: String) {
    public fun asString(): String = value
}
