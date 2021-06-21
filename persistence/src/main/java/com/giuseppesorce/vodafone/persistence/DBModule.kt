package com.giuseppesorce.vodafone.persistence


import android.content.Context
import com.giuseppesorce.vodafone.persistence.db.dao.*
import com.giuseppesorce.vodafone.persistence.db.MyDatabase


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

/**
 * @author Giuseppe Sorce
 */
@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun provideRegistrationDatabase(@ApplicationContext appContext: Context): MyDatabase {
        return MyDatabase.getAppDatabase(appContext)
    }

    @Provides
    @Singleton
    fun provideHeroesDaro(database: MyDatabase): HeroesDao {
        return database.heroesDao()
    }
}