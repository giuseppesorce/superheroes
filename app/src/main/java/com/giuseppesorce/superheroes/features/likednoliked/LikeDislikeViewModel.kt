package com.giuseppesorce.superheroes.features.likednoliked

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.giuseppesorce.superheroes.mappers.UIMapper
import com.giuseppesorce.superheroes.models.LIKE_PARAMETER
import com.giuseppesorce.superheroes.models.SuperHero
import com.giuseppesorce.superheroes.models.navigationevents.LikeEvents
import com.giuseppesorce.superheroes.models.navigationevents.LikeState
import com.giuseppesorce.vodafone.architecture.viewmodels.BaseFlowViewModel
import com.giuseppesorce.vodafone.domain.interactors.ChangeLikeSuperHeroUseCase
import com.giuseppesorce.vodafone.domain.interactors.GetCharacterLikeOrDislikedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

) : BaseFlowViewModel<LikeState, LikeEvents>() {

    private var isLikeList: Boolean=false

    private val superHeroes = MutableStateFlow(emptyList<SuperHero>())
    val superHeroesFlow: StateFlow<List<SuperHero>> = superHeroes

    var showEmpty= MutableStateFlow(false)
    var showEmptyFlow : StateFlow<Boolean> = showEmpty
    fun getHeroes() {

         isLikeList = handle.get<Boolean>(LIKE_PARAMETER) ?: false
        viewModelScope.launch {
            var heroes= getCharacterLikeOrDislikedUseCase.invoke(isLikeList)
            superHeroes.value=  uiMapper.getSuperHeroesListFromDb(heroes)
            showEmpty.value= superHeroes?.value?.isEmpty() ?: false


        }
    }

    fun onSelectLike(item: SuperHero) {
        changeSuperHero(item.id, true)
    }

     fun changeSuperHero(id: Int, like: Boolean) {

        viewModelScope.launch {
            var heroes= changeLikeSuperHeroUseCase.invoke(id, isLikeList, like)
            superHeroes.value=  uiMapper.getSuperHeroesListFromDb(heroes)
            showEmpty.value= superHeroes?.value.isEmpty() ?: true
        }
    }

    fun onSelectDislike(item: SuperHero) {
        changeSuperHero(item.id, false)
    }

    fun onSelectBack() {
       emitEvent(LikeEvents.GoBack)
    }

}