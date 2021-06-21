package com.giuseppesorce.vodafone.data.api


import com.giuseppesorce.vodafone.data.models.ErrorResponse
import com.giuseppesorce.vodafone.data.models.SCharactersResponse
import com.giuseppesorce.vodafone.data.network.ApiResult
import com.giuseppesorce.vodafone.data.network.DecodeErrorBody
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Giuseppe Sorce
 */
interface ApiService {
    @DecodeErrorBody
    @GET("v1/public/characters")
    suspend fun characters(
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("orderBy") orderBy: String,
        @Query("limit") limit: Int
    ): ApiResult<SCharactersResponse, ErrorResponse>

}