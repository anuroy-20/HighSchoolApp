package com.codetest.highschool

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.codetest.com.codetest.highschool.model.SatDetails
import com.codetest.com.codetest.highschool.repository.NetworkState
import com.codetest.highschool.model.HighSchoolListItem
import com.codetest.highschool.repository.MainRepository
import com.codetest.highschool.repository.MainRepositoryImpl
import com.codetest.highschool.viewmodel.DetailsViewModel
import com.codetest.highschool.viewmodel.GetDetailResponse
import com.codetest.highschool.viewmodel.GetSchoolListResponse
import com.codetest.highschool.viewmodel.HomeViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runner.notification.Failure
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DetailsViewModelClass {
    private val testDispatcher = TestCoroutineDispatcher()
    lateinit var detailsViewModel: DetailsViewModel
    private lateinit var mSatDetails: List<SatDetails>
    lateinit var mainRepository: MainRepositoryImpl

    @Mock
    lateinit var apiService: MainRepository

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        mainRepository = mock(MainRepositoryImpl::class.java)
        mSatDetails = listOf<SatDetails>(
            SatDetails(
                "02M260",
                "",
                "M",
                ",",
                "",
                ""
            )
        )

        detailsViewModel = DetailsViewModel(mainRepository)
    }

    @Test
    fun getSatDetails() {
        runBlocking {
            Mockito.`when`(mainRepository.getSatDetails())
                .thenReturn(NetworkState.Success(mSatDetails))
            detailsViewModel.getSatDetails()
            val result = GetDetailResponse.ResponseSuccess(mSatDetails).satDetails
            Assert.assertEquals(mSatDetails,result)
        }
    }

}