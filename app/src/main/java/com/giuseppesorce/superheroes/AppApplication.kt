package com.giuseppesorce.superheroes

import android.app.Application

import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * @author Giuseppe Sorce
 */


@HiltAndroidApp
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}