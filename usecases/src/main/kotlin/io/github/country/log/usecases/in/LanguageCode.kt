package io.github.country.log.usecases.`in`

@JvmInline
value class LanguageCode internal constructor(private val value: String) {
    fun code(): String = value
}
