package io.github.country.log.domain.usecase.scenarious

import arrow.core.Either
import io.github.country.log.domain.model.LanguageCode
import io.github.country.log.domain.model.LanguageRepository
import io.github.country.log.domain.model.errors.LanguageCodeCreationErrors
import io.github.country.log.domain.usecase.CreateLanguageCode
import io.github.country.log.domain.usecase.CreateLanguageCodeUseCaseErrors

public class CreateLanguageCodeUseCase(
    private val languageRepository: LanguageRepository
) : CreateLanguageCode {

    public override fun execute(value: String): Either<CreateLanguageCodeUseCaseErrors, LanguageCode> =
        LanguageCode.make(value, languageRepository).mapLeft {
            when (it) {
                is LanguageCodeCreationErrors.BlankLanguageCodeError -> it.toError()
                is LanguageCodeCreationErrors.LanguageCodeNotExistsError -> it.toError()
                is LanguageCodeCreationErrors.NotAtLanguageError -> it.toError()
            }
        }
}

internal fun LanguageCodeCreationErrors.BlankLanguageCodeError.toError()
        : CreateLanguageCodeUseCaseErrors.EmptyLanguageCode =
    CreateLanguageCodeUseCaseErrors.EmptyLanguageCode("Blank language code value")

internal fun LanguageCodeCreationErrors.LanguageCodeNotExistsError.toError()
        : CreateLanguageCodeUseCaseErrors.UnknownLanguageCode =
    CreateLanguageCodeUseCaseErrors.UnknownLanguageCode("Unknown language code")

internal fun LanguageCodeCreationErrors.NotAtLanguageError.toError()
        : CreateLanguageCodeUseCaseErrors.UnknownLanguageCode =
    CreateLanguageCodeUseCaseErrors.UnknownLanguageCode("Not at language code passed")
