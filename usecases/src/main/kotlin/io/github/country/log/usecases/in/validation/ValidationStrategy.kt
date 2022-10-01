package io.github.country.log.usecases.`in`.validation

sealed class ValidationStrategy {
    object FailFast : ValidationStrategy()

    object ErrorAccumulation : ValidationStrategy()
}
