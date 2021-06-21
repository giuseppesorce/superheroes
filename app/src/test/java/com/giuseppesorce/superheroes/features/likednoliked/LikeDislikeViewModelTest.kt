package com.giuseppesorce.superheroes.features.likednoliked

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.giuseppesorce.superheroes.CoroutinesTestRule
import com.giuseppesorce.superheroes.features.home.HomeViewModel
import com.giuseppesorce.superheroes.mappers.UIMapper
import com.giuseppesorce.vodafone.domain.interactors.ChangeLikeSuperHeroUseCase
import com.giuseppesorce.vodafone.domain.interactors.GetCharacterLikeOrDislikedUseCase
import com.giuseppesorce.vodafone.domain.interactors.GetCharactersUseCase
import com.giuseppesorce.vodafone.domain.interactors.SetCharacterLikeOrDislikedUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule

/**
 * @author Giuseppe Sorce
 */
class LikeDislikeViewModelTest (){




    lateinit var likeDislikeViewModel: LikeDislikeViewModel

    @MockK
    lateinit var getCharacterLikeOrDislikedUseCase: GetCharacterLikeOrDislikedUseCase

    lateinit var changeLikeSuperHeroUseCase: ChangeLikeSuperHeroUseCase

    @MockK
    lateinit var handle: SavedStateHandle

    @MockK
    lateinit var uiMapper: UIMapper


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        likeDislikeViewModel = LikeDislikeViewModel(handle,
            getCharacterLikeOrDislikedUseCase, changeLikeSuperHeroUseCase, uiMapper
        )
    }
}