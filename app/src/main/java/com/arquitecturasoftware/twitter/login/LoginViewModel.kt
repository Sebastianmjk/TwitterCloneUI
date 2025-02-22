package com.arquitecturasoftware.twitter.login

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arquitecturasoftware.twitter.api.ApiService
import com.arquitecturasoftware.twitter.api.RetrofitHelper
import com.arquitecturasoftware.twitter.api.TokenManager
import com.arquitecturasoftware.twitter.api.response.ProfilePhotoResponse
import com.arquitecturasoftware.twitter.api.response.TokenResponse
import dagger.hilt.android.internal.Contexts.getApplication
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import java.io.InputStream

class LoginViewModel : ViewModel() {

    private val apiService: ApiService = RetrofitHelper.api

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _newPassword = MutableLiveData<String>()
    val newPassword: LiveData<String> = _newPassword

    private val _isLoginEnable = MutableLiveData<Boolean>()
    val isLoginEnable: LiveData<Boolean> = _isLoginEnable

    private val _codigo = MutableLiveData<String>()
    val codigo: LiveData<String> = _codigo

    private val _isLoginEnablePassword = MutableLiveData<Boolean>()
    val isLoginEnablePassword: LiveData<Boolean> = _isLoginEnablePassword

    private val _isCodigoEnable = MutableLiveData<Boolean>()
    val isCodigoEnable: LiveData<Boolean> = _isCodigoEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoginEnableNewPassword = MutableLiveData<Boolean>()
    val isLoginNewEnablePassword: LiveData<Boolean> = _isLoginEnableNewPassword

    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri: LiveData<Uri?> = _imageUri

    private val _loginResult = MutableLiveData<TokenResponse?>()
    val loginResult: LiveData<TokenResponse?> = _loginResult

    private val _profilePhotoResponse = MutableLiveData<ProfilePhotoResponse?>()
    val profilePhotoResponse: LiveData<ProfilePhotoResponse?> = _profilePhotoResponse

    suspend fun login(email: String, password: String): Boolean {
        _isLoading.value = true
        return try {
            val response = apiService.getLogin(email, password)
            if (response.isSuccessful) {
                val tokenResponse = response.body()
                _loginResult.value = tokenResponse
                tokenResponse?.let {
                    TokenManager.accessToken = it.accessToken
                }
                true
            } else {
                _loginResult.value = null
                false
            }
        } catch (e: Exception) {
            _loginResult.value = null
            false
        } finally {
            _isLoading.value = false
        }
    }

    fun onLoginChangesNewPassword(password: String, newPassword: String) {
        _password.value = password
        _newPassword.value = newPassword
        _isLoginEnableNewPassword.value = enableLoginNewPassword(password, newPassword)
    }

    fun onCodigoChanges(codigo: String) {
        _codigo.value = codigo
        _isCodigoEnable.value = enableCodigo(codigo)
    }

    fun onLoginChangesEmail(email: String) {
        _email.value = email
        _isLoginEnable.value = enableLoginEmail(email)
    }

    fun onLoginChangesPassword(password: String) {
        _password.value = password
        _isLoginEnablePassword.value = enableLoginPassword(password)
    }

    fun setImageUri(uri: Uri?) {
        _imageUri.value = uri
    }

    suspend fun uploadProfilePhoto(inputStream: InputStream): Response<ProfilePhotoResponse> {
        val requestFile = inputStream.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", "profile_photo.jpg", requestFile)
        val token = "Bearer ${TokenManager.accessToken}"
        return apiService.postProfilePhoto(token, body)
    }

    fun updateProfilePhoto(context: Context, uri: Uri) {
        viewModelScope.launch {
            val inputStream = context.contentResolver.openInputStream(uri)
            inputStream?.let {
                val response = uploadProfilePhoto(it)
                if (response.isSuccessful) {
                    _profilePhotoResponse.value = response.body()
                    // Handle successful response
                } else {
                    // Handle error response
                }
            }
        }
    }

    private fun enableLoginEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun enableLoginPassword(password: String): Boolean {
        return password.length >= 8
    }

    private fun enableLoginNewPassword(password: String, newPassword: String): Boolean {
        return password == newPassword
    }

    private fun enableCodigo(codigo: String): Boolean {
        return true
    }
}