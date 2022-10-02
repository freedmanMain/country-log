package io.github.country.log.domain.model.rules

public sealed class ValidationStrategy {
    public object FailFast : ValidationStrategy()

    public object ErrorAccumulation : ValidationStrategy()
}
