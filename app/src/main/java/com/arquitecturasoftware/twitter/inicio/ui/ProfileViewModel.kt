package com.arquitecturasoftware.twitter.inicio.ui

import com.arquitecturasoftware.twitter.api.response.authservice.UpdateProfileRequest
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arquitecturasoftware.twitter.api.services.ApiService
import com.arquitecturasoftware.twitter.api.RetrofitHelper
import com.arquitecturasoftware.twitter.api.response.authservice.UsersProfileResponse
import com.arquitecturasoftware.twitter.api.response.authservice.VerifyTokenResponse
import com.arquitecturasoftware.twitter.api.services.AuthService
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val authService: AuthService = RetrofitHelper.authApi

    private val _profileUpdateResult = MutableLiveData<UsersProfileResponse?>()
    val profileUpdateResult: LiveData<UsersProfileResponse?> = _profileUpdateResult

    suspend fun updateProfile(token: String,name: String, password: String): Boolean {
        return try {
            val request = UpdateProfileRequest(name, password)
            val response = authService.updateProfile(token,request)
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

    private val _tokenData = MutableLiveData<VerifyTokenResponse?>()
    val tokenData: LiveData<VerifyTokenResponse?> = _tokenData

    fun fetchProfile(token: String) {
        viewModelScope.launch {
            try {
                val response = authService.getProfile(token)
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

    fun fetchTokenData(token: String) {
        viewModelScope.launch {
            try {
                val response = authService.verifyToken(token)
                if (response.isSuccessful) {
                    _tokenData.value = response.body()
                } else {
                    _tokenData.value = null
                    Log.e("ProfileViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _tokenData.value = null
                Log.e("ProfileViewModel", "Exception: ${e.message}")
            }
        }
    }


}