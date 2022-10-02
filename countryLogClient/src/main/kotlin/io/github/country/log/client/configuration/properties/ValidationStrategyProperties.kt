package io.github.country.log.client.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "validation")
class ValidationStrategyProperties {
    var strategy: Mode = Mode.FAIL_FAST

    enum class Mode {
        FAIL_FAST,
        ERROR_ACCUMULATION
    }
}
