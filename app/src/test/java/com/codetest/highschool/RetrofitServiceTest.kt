package com.codetest.highschool

import com.codetest.highschool.repository.MainRepository
import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitServiceTest {
    lateinit var mockWebServer: MockWebServer
    lateinit var apiService: MainRepository
    lateinit var gson: Gson

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        gson = Gson()
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(MainRepository::class.java)
    }


    @Test
    fun getHighSchooltest() {
        runBlocking {
            val mockResponse = MockResponse()
            mockWebServer.enqueue(mockResponse.setBody("[]"))
            val response = apiService.getHighSchoolList()
            val request = mockWebServer.takeRequest()
            assertEquals("/s3k6-pzi2.json", request.path)
            assertEquals(true, response.body()?.isEmpty() == true)
        }
    }

    @Test
    fun getSatDetailstest() {
        runBlocking {
            val mockResponse = MockResponse()
            mockWebServer.enqueue(mockResponse.setBody("[]"))
            val response = apiService.getSchoolSatDetails()
            val request = mockWebServer.takeRequest()
            assertEquals("/f9bf-2cp4.json", request.path)
            assertEquals(true, response.body()?.isEmpty() == true)
        }
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

}