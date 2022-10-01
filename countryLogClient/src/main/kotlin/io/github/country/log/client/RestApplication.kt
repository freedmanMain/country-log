package io.github.country.log.client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestApplication

fun main(args: Array<String>) {
    runApplication<RestApplication>(*args)
}
