package io.github.country.log.domain.usecase.scenarious

import arrow.core.Either
import io.github.country.log.domain.fakes.LocaleDataExtractorFake
import io.github.country.log.domain.model.CountryCode
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.model.errors.CountryNameNotFound
import io.github.country.log.domain.access.extraction.CountryName
import io.github.country.log.domain.usecase.GetCountryName
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class GetCountryNameUseCaseTests : BehaviorSpec({
    given("client has country and language codes") {
        `when`("country name was found") {
            then("client should receive data") {
                val language = LanguageCode("EN")
                val country = CountryCode("UA")
                val getCountryName: GetCountryName = GetCountryNameUseCase(LocaleDataExtractorFake())

                val result = getCountryName.execute(country, language)
                result.shouldBeInstanceOf<Either.Right<CountryName>>()
                result.value.asString() shouldBe "Ukraine"
            }
        }

        `when`("country name was not found") {
            then("client should receive error") {
                val getCountryName: GetCountryName = GetCountryNameUseCase(LocaleDataExtractorFake())
                val languageCode = LanguageCode("EN")
                val countryCode = CountryCode("RU")

                val result = getCountryName.execute(countryCode, languageCode)
                result.shouldBeInstanceOf<Either.Left<CountryNameNotFound>>()
            }
        }
    }
})
