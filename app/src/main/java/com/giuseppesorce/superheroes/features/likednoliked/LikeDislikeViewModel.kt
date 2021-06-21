package com.giuseppesorce.superheroes.features.likednoliked

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.giuseppesorce.superheroes.mappers.UIMapper
import com.giuseppesorce.superheroes.models.LIKE_PARAMETER
import com.giuseppesorce.superheroes.models.SuperHero
import com.giuseppesorce.superheroes.models.navigationevents.LikeEvents
import com.giuseppesorce.superheroes.models.navigationevents.LikeState
import com.giuseppesorce.vodafone.architecture.base.BaseViewModel
import com.giuseppesorce.vodafone.domain.interactors.ChangeLikeSuperHeroUseCase
import com.giuseppesorce.vodafone.domain.interactors.GetCharacterLikeOrDislikedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Giuseppe Sorce
 */

@HiltViewModel
class LikeDislikeViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val getCharacterLikeOrDislikedUseCase: GetCharacterLikeOrDislikedUseCase,
    private val changeLikeSuperHeroUseCase: ChangeLikeSuperHeroUseCase,
    private val uiMapper: UIMapper

) : BaseViewModel<LikeState, LikeEvents>() {

    private var isLikeList: Boolean=false
    var superHeroes: List<SuperHero>? = null
    var superHeroesLD = MutableLiveData<List<SuperHero>>()

    var showEmpty:Boolean= false
    var showEmptyLD = MutableLiveData<Boolean>()
    fun getHeroes() {

         isLikeList = handle.get<Boolean>(LIKE_PARAMETER) ?: false
        viewModelScope.launch {
            var heroes= getCharacterLikeOrDislikedUseCase.invoke(isLikeList)
            superHeroes= uiMapper.getSuperHeroesListFromDb(heroes)
            superHeroesLD.value= superHeroes
            showEmpty= superHeroes?.isEmpty() ?: true
            showEmptyLD.value= showEmpty

        }
    }

    fun onSelectLike(item: SuperHero) {
        changeSuperHero(item.id, true)
    }

    private fun changeSuperHero(id: Int, like: Boolean) {

        viewModelScope.launch {
            var heroes= changeLikeSuperHeroUseCase.invoke(id, isLikeList, like)
            superHeroes= uiMapper.getSuperHeroesListFromDb(heroes)
            superHeroesLD.value= superHeroes
            showEmpty= superHeroes?.isEmpty() ?: true
            showEmptyLD.value= showEmpty
        }
    }

    fun onSelectDislike(item: SuperHero) {
        changeSuperHero(item.id, false)
    }

    fun onSelectBack() {
       emitEvent(LikeEvents.GoBack)
    }


}