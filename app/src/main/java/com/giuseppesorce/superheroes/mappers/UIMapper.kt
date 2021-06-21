package com.giuseppesorce.superheroes.mappers


import com.giuseppesorce.superheroes.models.SuperHero
import com.giuseppesorce.vodafone.data.models.SCharactersResponse
import com.giuseppesorce.vodafone.data.models.SThumbnail
import com.giuseppesorce.vodafone.persistence.db.entities.RHero
import java.util.*
import javax.inject.Inject

class UIMapper @Inject constructor() {


    fun getSuperHeroesListFromDb(heroes: List<RHero>): List<SuperHero> =

        heroes.map {
            SuperHero(it.id, name = it.name, isLike = it.islike, image = it.image ?: "")

        }

    fun getSuperHeroesList(response: SCharactersResponse): List<SuperHero> {

        return response.data?.results?.map { character ->

            SuperHero(
                id = character.id ?: 0,
                name = character.name?.toUpperCase(Locale.getDefault()) ?: "SuperHero",
                image = getImage(character.thumbnail),
                description = character?.description ?: "",
                totalComics = (character?.comics?.available ?: 0).toString(),
                totalEvents = (character?.events?.available ?: 0).toString(),
                totalSeries = (character?.series?.available ?: 0).toString(),
                totalStories = (character?.stories?.available ?: 0).toString()
            )

        } ?: emptyList()


        return emptyList()
    }

     fun getImage(thumbnail: SThumbnail?): String {
        return thumbnail?.path?.let { path ->
            path + "." + thumbnail?.extension
        } ?: ""
    }
}