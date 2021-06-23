package com.giuseppesorce.superheroes.features.splash

import android.os.Handler
import android.os.Looper
import com.giuseppesorce.superheroes.models.navigationevents.MainEvents
import com.giuseppesorce.superheroes.models.navigationevents.MainState

import com.giuseppesorce.vodafone.architecture.viewmodels.BaseFlowViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Giuseppe Sorce
 */

@HiltViewModel
class SplashViewModel @Inject constructor(

) : BaseFlowViewModel<MainState, MainEvents>() {
    fun checkUser() {
        // this method is fake, it simulate cheking token or user check
        Handler(Looper.myLooper()!!).postDelayed({
           gotoHome()
        }, 1500)
    }

    private fun gotoHome() {
        emitEvent(MainEvents.GotoHome)
    }
}