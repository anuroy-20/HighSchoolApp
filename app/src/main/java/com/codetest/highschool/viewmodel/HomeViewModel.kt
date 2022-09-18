package com.codetest.highschool.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codetest.com.codetest.highschool.repository.NetworkState
import com.codetest.highschool.model.HighSchoolListItem
import com.codetest.highschool.repository.MainRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class GetSchoolListResponse {
    object ShowLoading : GetSchoolListResponse()
    object ResponseFailure : GetSchoolListResponse()
    data class ResponseSuccess(val highSchoolList: List<HighSchoolListItem>) :
        GetSchoolListResponse()
}

//ViewModel class for getting details from SchoolList API.
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepositoryImpl,
) : ViewModel() {
    //MutableLiveData to set a value on GetSchoolListResponse
    val _uiEvent = MutableLiveData<GetSchoolListResponse>()
    val res: LiveData<GetSchoolListResponse>
        get() = _uiEvent

    //Fetching School details from API
    fun getSchoolList() {
        _uiEvent.postValue(GetSchoolListResponse.ShowLoading)
        viewModelScope.launch {
            // Create coroutine that runs on Dispatchers.Default thread
            when (val response = mainRepository.getHighSchoolList()) {
                // Checking the results
                is NetworkState.Success -> {
                    _uiEvent.postValue(GetSchoolListResponse.ResponseSuccess(response.data))
                }
                is NetworkState.Error -> {
                    _uiEvent.postValue(GetSchoolListResponse.ResponseFailure)
                }
            }
        }
    }
}