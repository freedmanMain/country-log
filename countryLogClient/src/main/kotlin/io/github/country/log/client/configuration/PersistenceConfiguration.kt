package io.github.country.log.client.configuration

import io.github.country.log.domain.model.CountryRepository
import io.github.country.log.domain.model.LanguageRepository
import io.github.country.log.domain.access.LocaleDataExtractor
import io.github.country.log.inmem.persistence.InMemCountryRepository
import io.github.country.log.inmem.persistence.InMemLanguageRepository
import io.github.country.log.inmem.persistence.InMemLocaleDataExtractor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PersistenceConfiguration {

    @Bean
    fun localeDateExtractor(): LocaleDataExtractor = InMemLocaleDataExtractor()

    @Bean
    fun countryRepository(): CountryRepository = InMemCountryRepository()

    @Bean
    fun languageRepository(): LanguageRepository = InMemLanguageRepository()
}
