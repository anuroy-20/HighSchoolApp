package com.codetest.highschool.repository

import com.codetest.com.codetest.highschool.model.SatDetails
import com.codetest.com.codetest.highschool.repository.NetworkState
import com.codetest.highschool.model.HighSchoolListItem
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject

// Retrofit interface
// Making get request so displaying GET as annotation. and inside passing last parameter for url.
interface MainRepository {
    // Calling data from array
    // Calling it with json object and naming that methods as getHighSchoolList() and getSchoolSatDetails()
    @GET("s3k6-pzi2.json")
    suspend fun getHighSchoolList(): Response<List<HighSchoolListItem>>
    @GET("f9bf-2cp4.json")
    suspend fun getSchoolSatDetails(): Response<List<SatDetails>>
}

class MainRepositoryImpl @Inject constructor(val apiService: MainRepository) {
    //Creating getHighSchoolList to fetch details from getHighSchoolList
    suspend fun getHighSchoolList(): NetworkState<List<HighSchoolListItem>> {
        val response = apiService.getHighSchoolList()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }

    //Creating getSatDetails to fetch details from getSchoolSatDetails
    suspend fun getSatDetails(): NetworkState<List<SatDetails>> {
        val response = apiService.getSchoolSatDetails()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }
}

