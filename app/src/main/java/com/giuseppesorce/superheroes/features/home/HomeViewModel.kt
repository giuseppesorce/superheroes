package com.giuseppesorce.superheroes.features.home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.giuseppesorce.superheroes.R
import com.giuseppesorce.superheroes.mappers.UIMapper
import com.giuseppesorce.superheroes.models.SuperHero
import com.giuseppesorce.superheroes.models.navigationevents.*

import com.giuseppesorce.vodafone.architecture.viewmodels.BaseFlowViewModel
import com.giuseppesorce.vodafone.data.models.ErrorResponse
import com.giuseppesorce.vodafone.data.models.SCharactersResponse
import com.giuseppesorce.vodafone.data.network.ApiResult
import com.giuseppesorce.vodafone.data.network.ApiResult.Failure
import com.giuseppesorce.vodafone.domain.interactors.GetCharactersUseCase
import com.giuseppesorce.vodafone.domain.interactors.SetCharacterLikeOrDislikedUseCase
import com.yuyakaido.android.cardstackview.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * @author Giuseppe Sorce
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val setCharacterLikeOrDislikedUseCase: SetCharacterLikeOrDislikedUseCase,
    private val uiMapper: UIMapper

) : BaseFlowViewModel<HomeState, HomeEvents>() {


    var positionCard = 0
    var isInLike:Boolean= false


    private val superHeroes = MutableStateFlow(emptyList<SuperHero>())
    val superHeroesFlow: StateFlow<List<SuperHero>> = superHeroes

    fun loadCharacters() {
        showLoading()
        viewModelScope.launch {

            var result = getCharactersUseCase.invoke()
            hideLoading()
            when (result) {
                is ApiResult.Success -> createCharacters(result.response)
                is Failure -> when (result) {
                    is Failure.NetworkFailure -> checkError(result.error)
                    is Failure.HttpFailure -> checkErrorHttp(result)
                    is Failure.ApiFailure -> checkErrorApi(result.error)
                    is Failure.UnknownFailure -> checkErrorUnKnow(result.error)
                }
            }
        }
    }

     fun createCharacters(response: SCharactersResponse) {
         superHeroes.value= uiMapper.getSuperHeroesList(response)}

     fun checkErrorUnKnow(error: Throwable) {
        viewState = HomeState.ShowError(R.string.genericError)
    }


     fun checkErrorApi(error: ErrorResponse?) {
        Timber.tag("marvel").d("checkErrorApi: ${error}")
        viewState = HomeState.ShowError(R.string.genericError)
    }

     fun checkErrorHttp(result: Failure<ErrorResponse>) {
        var error = (result as? Failure.HttpFailure)?.error
        Timber.tag("marvel").d("checkErrorHttp: ${error?.message}")
        viewState = HomeState.ShowError(R.string.genericError)
    }


     fun checkError(error: IOException) {
        Timber.tag("marvel").d("IOException: ${error.localizedMessage}")
        viewState = HomeState.ShowError(R.string.genericError)
    }

    fun setPositionTopCard(position: Int) {
        positionCard = position
    }

    fun onCardDisappeared(position: Int) {

    }

    fun onCardSwiped(direction: Direction?) {
        var superHero = superHeroes?.value?.get(positionCard)
        when(isInLike){
            true -> viewState = HomeState.ShowLikeAnimation
            else -> viewState = HomeState.ShowDisLikeAnimation
        }
        superHero?.let { superHero ->
            viewModelScope.launch {
                setCharacterLikeOrDislikedUseCase.invoke(
                    superHero.id,
                    isInLike,
                    superHero.name,
                    superHero.image,
                    superHero.description ,
                    superHero.totalComics,
                    superHero.totalEvents,
                    superHero.totalSeries,
                    superHero.totalStories
                )
            }
        }
    }

    fun onCardDragging(direction: Direction?) {
       when(direction){
           Direction.Left -> {
               isInLike= false

           }
           Direction.Right -> {
               isInLike= true
           }
       }
    }

    fun onSelectLikeButton() {
        emitEvent(HomeEvents.GotoLikedOrDislikeHeroes(true))
    }

    fun onSelectDislikeButton() {
        emitEvent(HomeEvents.GotoLikedOrDislikeHeroes(false))
    }

}