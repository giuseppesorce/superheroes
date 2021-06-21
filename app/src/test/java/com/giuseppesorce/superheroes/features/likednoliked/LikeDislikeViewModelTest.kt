package com.giuseppesorce.superheroes.features.likednoliked

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.giuseppesorce.superheroes.CoroutinesTestRule
import com.giuseppesorce.superheroes.features.home.HomeViewModel
import com.giuseppesorce.superheroes.mappers.UIMapper
import com.giuseppesorce.superheroes.models.LIKE_PARAMETER
import com.giuseppesorce.superheroes.models.SuperHero
import com.giuseppesorce.vodafone.data.models.SCharactersResponse
import com.giuseppesorce.vodafone.data.network.ApiResult
import com.giuseppesorce.vodafone.domain.interactors.ChangeLikeSuperHeroUseCase
import com.giuseppesorce.vodafone.domain.interactors.GetCharacterLikeOrDislikedUseCase
import com.giuseppesorce.vodafone.domain.interactors.GetCharactersUseCase
import com.giuseppesorce.vodafone.domain.interactors.SetCharacterLikeOrDislikedUseCase
import com.giuseppesorce.vodafone.persistence.db.entities.RHero
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import junit.framework.TestCase
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author Giuseppe Sorce
 */
class LikeDislikeViewModelTest (){


    lateinit var likeDislikeViewModel: LikeDislikeViewModel

    @MockK
    lateinit var getCharacterLikeOrDislikedUseCase: GetCharacterLikeOrDislikedUseCase

    @MockK
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

    @Test
    fun `test get heroes and superheroes is not empty`()  =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            coEvery { handle.get<Boolean>(any())  } returns true
            coEvery { getCharacterLikeOrDislikedUseCase.invoke(any()) } returns listOf<RHero>()
            coEvery { uiMapper.getSuperHeroesListFromDb(any()) } returns listOf<SuperHero>(SuperHero(0, ""))
            likeDislikeViewModel.getHeroes()
            Assert.assertEquals(false, likeDislikeViewModel.superHeroes?.isEmpty())
        }


    @Test
    fun `test get heroes and showEmptyLD true`()  =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            coEvery { handle.get<Boolean>(any())  } returns true
            coEvery { getCharacterLikeOrDislikedUseCase.invoke(any()) } returns listOf<RHero>()
            coEvery { uiMapper.getSuperHeroesListFromDb(any()) } returns emptyList<SuperHero>()
            likeDislikeViewModel.getHeroes()
            Assert.assertEquals(true, likeDislikeViewModel.showEmptyLD.value)
        }
////
////
    @Test
    fun `test changeSuperHero hero like and superheroes is showEmptyLD true`()  =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            coEvery { changeLikeSuperHeroUseCase.invoke(any(), any(), any()) } returns listOf<RHero>()
            coEvery { uiMapper.getSuperHeroesListFromDb(any()) } returns emptyList<SuperHero>()
            likeDislikeViewModel.changeSuperHero(1, true)
            Assert.assertEquals(true, likeDislikeViewModel.showEmptyLD.value)
        }
}