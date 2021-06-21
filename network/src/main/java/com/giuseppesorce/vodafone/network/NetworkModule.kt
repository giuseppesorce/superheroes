package com.giuseppesorce.vodafone.network


import com.giuseppesorce.vodafone.network.data.NetworkEnvironment
import com.giuseppesorce.network.interceptors.AuthHeaderInterceptor
import com.giuseppesorce.network.interceptors.logging.AndroidLoggingInterceptor
import com.giuseppesorce.vodafone.data.api.ApiService
import com.giuseppesorce.vodafone.data.network.ApiResultCallAdapterFactory
import com.giuseppesorce.vodafone.data.network.ApiResultConverterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Giuseppe Sorce
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(
        authHeaderInterceptor: AuthHeaderInterceptor
    ): OkHttpClient {

        val builder = OkHttpClient.Builder()
            .connectTimeout(160, TimeUnit.SECONDS)
            .readTimeout(160, TimeUnit.SECONDS)
            .writeTimeout(160, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
        val loggingInterceptor = AndroidLoggingInterceptor.build(
            hideVerticalLine = true,
            requestTag = "server",
            responseTag = "server"
        )
        builder.addInterceptor(loggingInterceptor)
        builder.addInterceptor(authHeaderInterceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        json: Json,
        okHttpClient: OkHttpClient, environment: NetworkEnvironment
    ): Retrofit {
        val contentType = "application/json".toMediaType() //content-type:
        return Retrofit.Builder()
            .baseUrl(environment.base_url)
            .client(okHttpClient)
            .addConverterFactory(ApiResultConverterFactory)
            .addCallAdapterFactory(ApiResultCallAdapterFactory)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }


    @Singleton
    @Provides
    fun provideEnviroment() = NetworkEnvironment()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideJsonConfiguration(): Json {
        return Json {
            isLenient = true
            prettyPrint = true
            ignoreUnknownKeys = true
        }
    }
}