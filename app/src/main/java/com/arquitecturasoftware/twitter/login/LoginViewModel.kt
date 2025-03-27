package com.arquitecturasoftware.twitter.login

import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arquitecturasoftware.twitter.api.RetrofitHelper
import com.arquitecturasoftware.twitter.api.TokenManager
import com.arquitecturasoftware.twitter.api.response.authservice.TokenResponse
import com.arquitecturasoftware.twitter.api.services.AuthService

class LoginViewModel  :ViewModel() {

    private val authService: AuthService = RetrofitHelper.authApi

    private val _nombre = MutableLiveData<String>()
    val nombre : LiveData<String> = _nombre

    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _newPassword = MutableLiveData<String>()
    val newPassword : LiveData<String> = _newPassword

    private val _isLoginEnable = MutableLiveData<Boolean>()
    val isLoginEnable : LiveData<Boolean> = _isLoginEnable

    private val _codigo = MutableLiveData<String>()
    val codigo : LiveData<String> = _codigo

    private val _isLoginEnablePassword = MutableLiveData<Boolean>()
    val isLoginEnablePassword : LiveData<Boolean> = _isLoginEnablePassword

    private val _isCodigoEnable = MutableLiveData<Boolean>()
    val isCodigoEnable : LiveData<Boolean> = _isCodigoEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isLoginEnableNewPassword = MutableLiveData<Boolean>()
    val isLoginNewEnablePassword : LiveData<Boolean> = _isLoginEnableNewPassword

    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri: LiveData<Uri?> = _imageUri

    private val _loginResult = MutableLiveData<TokenResponse?>()
    val loginResult: LiveData<TokenResponse?> = _loginResult

    suspend fun login(email: String, password: String): Boolean {
        _isLoading.value = true
        return try {
            val response = authService.getLogin(email, password)
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

    fun setNombre(nombre: String) {
        _nombre.value = nombre
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun onLoginChangesNewPassword(password:String, newPassword:String){
        _password.value = password
        _newPassword.value = newPassword
        _isLoginEnableNewPassword.value = enableLoginNewPassword(password, newPassword)
    }


    fun onCodigoChanges(codigo:String){
        _codigo.value = codigo
        _isCodigoEnable.value = enableCodigo(codigo)
    }

    fun onLoginChangesEmail(email:String) {
        _email.value = email
        _isLoginEnable.value = enableLoginEmail(email)
    }

    fun onLoginChangesPassword(password:String){
        _password.value = password
        _isLoginEnablePassword.value = enableLoginPassword(password)
    }

    fun setImageUri(uri: Uri?) {
        _imageUri.value = uri
    }

    private fun enableLoginEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun enableLoginPassword(password: String): Boolean {
        return password.length >= 8
    }

    private fun enableLoginNewPassword(password: String, newPassword:String): Boolean {
        return  password == newPassword
    }

    private fun enableCodigo(codigo: String): Boolean {
        return true
    }

}