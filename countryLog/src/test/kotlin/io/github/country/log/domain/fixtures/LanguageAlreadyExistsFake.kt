package io.github.country.log.domain.fixtures

import io.github.country.log.domain.model.LanguageCodeRequest
import io.github.country.log.domain.services.LanguageAlreadyExists

class LanguageAlreadyExistsFake : LanguageAlreadyExists {
    private val inMemDb = mutableMapOf<LanguageCodeRequest, String>()
        .apply {
            this[LanguageCodeRequest("EN")] = """ { code: "UA" } """
            this[LanguageCodeRequest("UA")] = """ { code: "EN" } """
        }

    override fun check(request: LanguageCodeRequest): Boolean = inMemDb[request] != null
}
