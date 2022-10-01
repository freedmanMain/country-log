package io.github.country.log.usecases.`in`.rules

sealed class Strategy {
    object FailFast : Strategy()

    object ErrorAccumulation : Strategy()
}
