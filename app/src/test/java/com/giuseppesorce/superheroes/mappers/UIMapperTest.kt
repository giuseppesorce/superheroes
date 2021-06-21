package com.giuseppesorce.superheroes.mappers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.giuseppesorce.superheroes.CoroutinesTestRule
import com.giuseppesorce.superheroes.features.home.HomeViewModel
import com.giuseppesorce.vodafone.data.models.SCharactersResponse
import com.giuseppesorce.vodafone.data.models.SThumbnail
import com.giuseppesorce.vodafone.data.network.ApiResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import junit.framework.TestCase
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

/**
 * @author Giuseppe Sorce
 */
class UIMapperTest {


    lateinit var uiMapper: UIMapper


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        uiMapper = UIMapper()
    }
//
//
    @Test
    fun `test getImage when thumbnail is null and url is empty `() {

        var url = uiMapper.getImage(null)
        assertEquals(true, url.isEmpty())
    }

    @Test
    fun `test getImage when thumbnail ha value and url is no empty `() {
        var path= "http://www.marvel.it/image"
        var extension= "png"
        var url = uiMapper.getImage(SThumbnail(path, extension))
        assertEquals(true, url.equals("$path.$extension"))
    }
}