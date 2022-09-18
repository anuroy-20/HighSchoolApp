package com.codetest.com.codetest.highschool.repository

import retrofit2.Response

//Setting Success and Error stage to set data according to the API response
sealed class NetworkState<out T> {
    data class Success<out T>(val data: T): NetworkState<T>()
    data class Error<T>(val response: Response<T>): NetworkState<T>()
}
