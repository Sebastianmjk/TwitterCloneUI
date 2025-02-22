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

    suspend fun updateProfile(token: String,name: String, password: String): Boolean {
        return try {
            val request = UpdateProfileRequest(name, password)
            val response = apiService.updateProfile(token,request)
            if (response.isSuccessful) {
                _profileUpdateResult.value = response.body()
                Log.i("ProfileViewModel", "Response: ${response.body()}")
                true
            } else {
                _profileUpdateResult.value = null
                Log.i("ProfileViewModel", "Error: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            _profileUpdateResult.value = null
            Log.e("ProfileViewModel", "Exception: ${e.message}")
            false
        }
    }

    private val _profile = MutableLiveData<UsersProfileResponse?>()
    val profile: LiveData<UsersProfileResponse?> = _profile

    fun fetchProfile(token: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getProfile(token)
                if (response.isSuccessful) {
                    _profile.value = response.body()
                } else {
                    _profile.value = null
                    Log.e("ProfileViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _profile.value = null
                Log.e("ProfileViewModel", "Exception: ${e.message}")
            }
        }
    }
}