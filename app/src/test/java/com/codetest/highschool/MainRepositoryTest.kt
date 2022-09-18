package com.codetest.highschool

import com.codetest.com.codetest.highschool.model.SatDetails
import com.codetest.com.codetest.highschool.repository.NetworkState
import com.codetest.highschool.model.HighSchoolListItem
import com.codetest.highschool.repository.MainRepository
import com.codetest.highschool.repository.MainRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class MainRepositoryTest {

    lateinit var mainRepository: MainRepositoryImpl

    @Mock
    lateinit var apiService: MainRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mainRepository = MainRepositoryImpl(apiService)
    }

    @Test
    fun gethighSchooltest() {
        runBlocking {
            Mockito.`when`(apiService.getHighSchoolList()).thenReturn(Response.success(listOf<HighSchoolListItem>()))
            val response = mainRepository.getHighSchoolList()
            assertEquals(listOf<HighSchoolListItem>(),  NetworkState.Success(response).data)
        }
    }

    @Test
    fun getSatDetailstest() {
        runBlocking {
            Mockito.`when`(apiService.getSchoolSatDetails()).thenReturn(Response.success(listOf<SatDetails>()))
            val response = mainRepository.getSatDetails()
            assertEquals(listOf<HighSchoolListItem>(),  NetworkState.Success(response).data)
        }
    }
}