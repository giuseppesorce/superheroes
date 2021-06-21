package com.giuseppesorce.superheroes.features.main

import dagger.hilt.android.lifecycle.HiltViewModel

import com.giuseppesorce.superheroes.models.navigationevents.MainEvents
import com.giuseppesorce.superheroes.models.navigationevents.MainState
import com.giuseppesorce.vodafone.architecture.base.BaseViewModel
import javax.inject.Inject

/**
 * @author Giuseppe Sorce
 */
@HiltViewModel
class MainViewModel  @Inject constructor(
) : BaseViewModel<MainState, MainEvents>() {

}