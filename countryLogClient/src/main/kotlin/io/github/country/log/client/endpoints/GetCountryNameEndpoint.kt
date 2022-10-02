package io.github.country.log.client.endpoints

import arrow.core.Either
import io.github.country.log.client.endpoints.model.CountryNameResponse
import io.github.country.log.client.rest.InvalidParams
import io.github.country.log.client.rest.resourceNotFound
import io.github.country.log.client.rest.toBadRequest
import io.github.country.log.domain.access.extraction.CountryName
import io.github.country.log.domain.model.errors.CountryNameNotFound
import io.github.country.log.domain.usecase.CreateCountryCode
import io.github.country.log.domain.usecase.CreateCountryCodeUseCaseErrors
import io.github.country.log.domain.usecase.CreateLanguageCode
import io.github.country.log.domain.usecase.CreateLanguageCodeUseCaseErrors
import io.github.country.log.domain.usecase.GetCountryName
import org.springframework.hateoas.mediatype.problem.Problem
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GetCountryNameEndpoint(
    private val createCountryCode: CreateCountryCode,
    private val createLanguageCode: CreateLanguageCode,
    private val getCountryName: GetCountryName
) {

    @GetMapping(path = [API_V1_COUNTRY_GET_NAME])
    fun get(@PathVariable id: String, @RequestParam(required = true) lang: String): ResponseEntity<*> {

        val country = when (val result = createCountryCode.execute(id)) {
            is Either.Right -> result.value
            is Either.Left -> return result.value.toRestError()
        }

        val language = when (val result = createLanguageCode.execute(lang)) {
            is Either.Right -> result.value
            is Either.Left -> return result.value.toRestError()
        }

        return getCountryName.execute(country, language)
            .fold({ it.toRestError() }, { ResponseEntity.ok(it.toResponse()) })
    }

    private fun CountryName.toResponse(): CountryNameResponse =
        CountryNameResponse(this.asString())

    private fun CreateCountryCodeUseCaseErrors.toRestError(): ResponseEntity<Problem> =
        InvalidParams(this.message).toBadRequest()

    private fun CreateLanguageCodeUseCaseErrors.toRestError(): ResponseEntity<Problem> =
        InvalidParams(this.message).toBadRequest()

    private fun CountryNameNotFound.toRestError(): ResponseEntity<Problem> = resourceNotFound()
}
