package com.giuseppesorce.superheroes.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.giuseppesorce.superheroes.CoroutinesTestRule
import com.giuseppesorce.superheroes.mappers.UIMapper
import com.giuseppesorce.superheroes.models.SuperHero
import com.giuseppesorce.superheroes.models.navigationevents.HomeState
import com.giuseppesorce.vodafone.data.models.SCharactersResponse
import com.giuseppesorce.vodafone.data.network.ApiResult
import com.giuseppesorce.vodafone.domain.interactors.GetCharactersUseCase
import com.giuseppesorce.vodafone.domain.interactors.SetCharacterLikeOrDislikedUseCase
import com.yuyakaido.android.cardstackview.Direction
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.junit.Assert.*
import java.io.IOException

/**
 * @author Giuseppe Sorce
 */
class HomeViewModelTest {

    lateinit var homeViewModel:HomeViewModel<Any?,Any?>

    @MockK
    lateinit var getCharactersUseCase: GetCharactersUseCase

    @MockK
    lateinit var setCharacterLikeOrDislikedUseCase: SetCharacterLikeOrDislikedUseCase

    @MockK
    lateinit var uiMapper: UIMapper


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        homeViewModel = HomeViewModel(
            getCharactersUseCase, setCharacterLikeOrDislikedUseCase, uiMapper
        )
    }


    @Test
    fun `test when load characters success and characters list is not empty`()  =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            coEvery { getCharactersUseCase.invoke() } returns ApiResult.success(SCharactersResponse())
            coEvery { uiMapper.getSuperHeroesList(any()) } returns listOf<SuperHero>(SuperHero(0, ""))
            homeViewModel.createCharacters(SCharactersResponse())
            assertEquals(true, homeViewModel.superHeroesLD.value?.isNotEmpty())
        }


    @Test
    fun `test when load characters success and characters list is null`()  =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            coEvery { getCharactersUseCase.invoke() } returns ApiResult.success(SCharactersResponse())
            coEvery { uiMapper.getSuperHeroesList(any()) } returns emptyList()
            assertNull( homeViewModel.superHeroesLD.value)
        }


    @Test
    fun `test when load characters failed `()  =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            coEvery { getCharactersUseCase.invoke() } returns ApiResult.Failure.HttpFailure(0,null)
            homeViewModel.checkError(IOException())
            assertEquals(true,  homeViewModel.stateLiveData.value is  HomeState.ShowError)
        }

    @Test
    fun `test when onCardDragging direction right `() {
        homeViewModel.onCardDragging(Direction.Right)
        assertTrue(  homeViewModel.isInLike )
    }

    @Test
    fun `test when onCardDragging direction left `() {
        homeViewModel.onCardDragging(Direction.Left)
        assertFalse(  homeViewModel.isInLike )
    }
}