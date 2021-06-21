package com.giuseppesorce.vodafone.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.giuseppesorce.vodafone.data.CoroutinesTestRule
import com.giuseppesorce.vodafone.data.api.ApiService
import com.giuseppesorce.vodafone.data.mappers.DataMapper
import com.giuseppesorce.vodafone.data.models.ErrorResponse
import com.giuseppesorce.vodafone.data.models.SCharactersResponse
import com.giuseppesorce.vodafone.data.network.ApiResult
import com.giuseppesorce.vodafone.persistence.db.dao.HeroesDao
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @author Giuseppe Sorce
 */
class CharactersRepositoryTest {


    lateinit var charactersRepository: CharactersRepository


    @MockK(relaxed = true)
    lateinit var apiService: ApiService

    @MockK
    lateinit var heroesDao: HeroesDao


    @MockK
    lateinit var dataMapper: DataMapper


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        charactersRepository = CharactersRepository(apiService, heroesDao, dataMapper)
    }

    @Test
    fun `test get heroes and showEmptyLD true`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            coEvery {
                apiService.characters(any(), any(), any(), any(), any())
            } returns ApiResult.success(SCharactersResponse())

            var result = apiService.characters("", "", "", "", 100)
            assertTrue(result is ApiResult.Success<*>)
            assertNotNull(result)
        }
}