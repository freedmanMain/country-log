package io.github.country.log.usecases.out

@JvmInline
value class CountryI18N(private val value: String) {
    fun i18n(): String = value
}
