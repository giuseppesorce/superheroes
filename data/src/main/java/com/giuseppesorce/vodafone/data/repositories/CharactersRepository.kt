package com.giuseppesorce.vodafone.data.repositories


import com.giuseppesorce.vodafone.commons.encrypt.toMD5
import com.giuseppesorce.vodafone.data.BuildConfig
import com.giuseppesorce.vodafone.data.api.ApiService
import com.giuseppesorce.vodafone.data.mappers.DataMapper
import com.giuseppesorce.vodafone.data.models.ErrorResponse
import com.giuseppesorce.vodafone.data.models.SCharactersResponse
import com.giuseppesorce.vodafone.data.network.ApiResult
import com.giuseppesorce.vodafone.persistence.db.dao.HeroesDao
import com.giuseppesorce.vodafone.persistence.db.entities.RHero
import java.util.*
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val apiService: ApiService,
    private val heroesDao: HeroesDao, private val dataMapper: DataMapper
) {

    suspend fun characters(): ApiResult<SCharactersResponse, ErrorResponse> {

        var timeStamp = Date().time.toString()
        var hash = getHashKey(timeStamp)
        return apiService.characters(
            ts = timeStamp,
            apikey = BuildConfig.MARVELPUBLICKEY,
            hash = hash,
            orderBy = "-modified",
            limit = 50
        )
    }

    private fun getHashKey(timeStamp: String): String {

        var stringToHash = timeStamp + BuildConfig.MARVELPRIVATEKEY + BuildConfig.MARVELPUBLICKEY
        return stringToHash.toMD5()
    }

  suspend  fun setHeroLiked(
        id: Int,
        name: String,
        image: String,
        description: String,
        totalComics: String,
        totalEvents: String,
        totalSeries: String,
        totalStories: String
    ) {
        heroesDao.insert(dataMapper.getHeroEntity(id, true,  name, image, description, totalComics, totalEvents,totalSeries,totalStories))
    }


    suspend  fun setHeroDisLiked(
        id: Int,
        name: String,
        image: String,
        description: String,
        totalComics: String,
        totalEvents: String,
        totalSeries: String,
        totalStories: String
    ) {
        heroesDao.insert(dataMapper.getHeroEntity(id, false,  name, image, description, totalComics, totalEvents,totalSeries,totalStories))
    }

    suspend fun getHeroes(like: Boolean): List<RHero> = heroesDao.getLikedHeroes(when(like){
        true -> 1
        else -> 0
    })

    fun changeLikeSuperHero(id: Int, like: Boolean) {
        heroesDao.changeLike(id, when(like){
            true -> 1
            else -> 0
        })
    }


}

