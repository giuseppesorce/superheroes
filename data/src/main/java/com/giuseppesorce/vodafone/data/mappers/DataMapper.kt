package com.giuseppesorce.vodafone.data.mappers

import com.giuseppesorce.vodafone.persistence.db.entities.RHero
import javax.inject.Inject

class DataMapper @Inject constructor() {
    fun getHeroEntity( id: Int,
                        isLike:Boolean,
                       name: String,
                       image: String,
                       description: String,
                       totalComics: String,
                       totalEvents: String,
                       totalSeries: String,
                       totalStories: String): RHero {

        return RHero(

            id = id,
            islike = isLike,
            name= name,
            description= description,
            image = image,
            totalComics = totalComics,
            totalEvents = totalEvents,
            totalSeries = totalSeries,
            totalStories = totalStories

        )



    }



}