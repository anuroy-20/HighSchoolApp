package com.codetest.highschool.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codetest.com.codetest.highschool.model.SatDetails
import com.codetest.com.codetest.highschool.repository.NetworkState
import com.codetest.highschool.repository.MainRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class GetDetailResponse {
    object ShowLoading : GetDetailResponse()
    object ResponseFailure : GetDetailResponse()
    data class ResponseSuccess(val satDetails: List<SatDetails>) :
        GetDetailResponse()
}

//ViewModel class for getting details from Sat Details API.
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val mainRepository: MainRepositoryImpl,
) : ViewModel() {
    //MutableLiveData to set a value on GetDetailResponse
    val _uiEvent = MutableLiveData<GetDetailResponse>()
    val res: LiveData<GetDetailResponse>
        get() = _uiEvent

    //Fetching sat details from API
    fun getSatDetails() {
        _uiEvent.postValue(GetDetailResponse.ShowLoading)
        viewModelScope.launch {
            // Create coroutine that runs on Dispatchers.Default thread
            when (val response = mainRepository.getSatDetails()) {
                // Checking the results
                is NetworkState.Success -> {
                    _uiEvent.postValue(GetDetailResponse.ResponseSuccess(response.data))
                }
                is NetworkState.Error -> {
                    _uiEvent.postValue(GetDetailResponse.ResponseFailure)
                }
            }
        }
    }
}