package io.github.country.log.client.configuration

import io.github.country.log.domain.access.LocaleDataExtractor
import io.github.country.log.domain.model.CountryRepository
import io.github.country.log.domain.model.LanguageRepository
import io.github.country.log.domain.usecase.CreateCountryCode
import io.github.country.log.domain.usecase.CreateLanguageCode
import io.github.country.log.domain.usecase.GetCountryName
import io.github.country.log.domain.usecase.scenarious.CreateCountryCodeUseCase
import io.github.country.log.domain.usecase.scenarious.CreateLanguageCodeUseCase
import io.github.country.log.domain.usecase.scenarious.GetCountryNameUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(value = [PersistenceConfiguration::class])
class UseCaseConfiguration {

    @Bean
    fun getCountryName(localeDataExtractor: LocaleDataExtractor): GetCountryName =
        GetCountryNameUseCase(localeDataExtractor)

    @Bean
    fun createCountryCode(countryRepository: CountryRepository): CreateCountryCode =
        CreateCountryCodeUseCase(countryRepository)

    @Bean
    fun createLanguageCode(languageRepository: LanguageRepository): CreateLanguageCode =
        CreateLanguageCodeUseCase(languageRepository)
}
