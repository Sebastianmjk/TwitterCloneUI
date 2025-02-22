package com.arquitecturasoftware.twitter.inicio.ui

import UpdateProfileRequest
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arquitecturasoftware.twitter.api.ApiService
import com.arquitecturasoftware.twitter.api.RetrofitHelper
import com.arquitecturasoftware.twitter.api.response.UsersProfileResponse
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val apiService: ApiService = RetrofitHelper.api

    private val _profileUpdateResult = MutableLiveData<UsersProfileResponse?>()
    val profileUpdateResult: LiveData<UsersProfileResponse?> = _profileUpdateResult

    fun updateProfile(name: String, password: String) {
        viewModelScope.launch {
            try {
                val request = UpdateProfileRequest(name, password)
                val response = apiService.updateProfile(request)
                if (response.isSuccessful) {
                    _profileUpdateResult.value = response.body()
                    Log.i("ProfileViewModel", "Response: ${response.body()}")
                } else {
                    _profileUpdateResult.value = null
                    Log.i("ProfileViewModel", "Response: ${response.body()}")
                }
            } catch (e: Exception) {
                _profileUpdateResult.value = null
            }
        }
    }
}