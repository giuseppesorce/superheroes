package com.giuseppesorce.vodafone.domain.interactors

import com.giuseppesorce.vodafone.data.models.ErrorResponse
import com.giuseppesorce.vodafone.data.models.SCharactersResponse
import com.giuseppesorce.vodafone.data.network.ApiResult
import com.giuseppesorce.vodafone.data.repositories.CharactersRepository
import com.giuseppesorce.vodafone.persistence.db.entities.RHero
import java.util.*
import javax.inject.Inject

/**
 * @author Giuseppe Sorce
 */


class GetCharactersUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {
    suspend operator fun invoke(
    ): ApiResult<SCharactersResponse, ErrorResponse> {
        return charactersRepository.characters()
    }
}

class SetCharacterLikeOrDislikedUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {
    suspend operator fun invoke(
        id: Int,
        isLike: Boolean,
        name: String,
        image: String,
        description: String,
        totalComics: String,
        totalEvents: String,
        totalSeries: String,
        totalStories: String
    ) {
        when (isLike) {

            true -> charactersRepository.setHeroLiked(
                id,
                name,
                image,
                description,
                totalComics,
                totalEvents,
                totalSeries,
                totalStories
            )
            else -> charactersRepository.setHeroDisLiked(
                id,
                name,
                image,
                description,
                totalComics,
                totalEvents,
                totalSeries,
                totalStories
            )
        }
    }
}

class GetCharacterLikeOrDislikedUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {
    suspend operator fun invoke(
        isLike: Boolean
    ): List<RHero> = charactersRepository.getHeroes(isLike)
}

class ChangeLikeSuperHeroUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {
    suspend operator fun invoke(
        id: Int,
        typeList: Boolean,
        isLike: Boolean
    ): List<RHero> {
        charactersRepository.changeLikeSuperHero(id, isLike)


        return charactersRepository.getHeroes(typeList)
    }

}