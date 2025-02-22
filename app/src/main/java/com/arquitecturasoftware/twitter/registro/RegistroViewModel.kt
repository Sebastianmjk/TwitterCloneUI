package com.arquitecturasoftware.twitter.registro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arquitecturasoftware.twitter.api.ApiService
import com.arquitecturasoftware.twitter.api.RetrofitHelper
import com.arquitecturasoftware.twitter.api.response.RegisterRequest
import com.arquitecturasoftware.twitter.api.response.UsersProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistroViewModel @Inject constructor() : ViewModel() {

    private val apiService: ApiService = RetrofitHelper.api

    private val _nombre = MutableLiveData<String>()
    val nombre : LiveData<String> = _nombre

    private val _fechaNacimiento = MutableLiveData<String>()
    val fechaNacimiento : LiveData<String> = _fechaNacimiento

    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _isRegistroEnable = MutableLiveData<Boolean>()
    val isRestigroEnable : LiveData<Boolean> = _isRegistroEnable

    private val _codigo = MutableLiveData<String>()
    val codigo : LiveData<String> = _codigo

    private val _isCodigoEnable = MutableLiveData<Boolean>()
    val isCodigoEnable : LiveData<Boolean> = _isCodigoEnable

    private val _arrobaNombre = MutableLiveData<String>()
    val arrobaNombre : LiveData<String> = _arrobaNombre

    private val _isRegisterEnable = MutableLiveData<Boolean>()
    val isRegisterEnable : LiveData<Boolean> = _isRegisterEnable

    private val _isRegistroEnablePassword = MutableLiveData<Boolean>()
    val isRegistroEnablePassword : LiveData<Boolean> = _isRegistroEnablePassword

    private val _isArrobaNameEnbable = MutableLiveData<Boolean>()
    val isArrobaNameEnbable : LiveData<Boolean> = _isArrobaNameEnbable

    private val _registrationResult = MutableLiveData<UsersProfileResponse?>()
    val registrationResult: LiveData<UsersProfileResponse?> = _registrationResult

    fun onRegistroChangesEmail(email:String){
        _email.value = email
        _isRegistroEnable.value = enableRegistroEmail(email)
    }

    fun onCodigoChanges(codigo:String){
        _codigo.value = codigo
        _isCodigoEnable.value = enableCodigo(codigo)
    }

    fun onRegistroChangesArrobaNombre(arrobaNombre:String){
        _arrobaNombre.value = arrobaNombre
        _isArrobaNameEnbable.value = enableArrobaName(arrobaNombre)
    }

    fun onRegistroChangesPassword(password:String){
        _password.value = password
        _isRegistroEnablePassword.value = enableRegistroPassword(password)
    }

    fun onRegistroChanges(nombre:String, email:String){
        _nombre.value = nombre
        _email.value = email
        _isRegisterEnable.value = enableRegisto(
            nombre, email,
        )
    }

    fun registerUser() {
        viewModelScope.launch {
            try {
                val request = RegisterRequest(
                    name = _nombre.value ?: "",
                    email = _email.value ?: "",
                    password = _password.value ?: "",
                    fullName = _arrobaNombre.value ?: ""
                )
                val response = apiService.registerUser(request)
                if (response.isSuccessful) {
                    _registrationResult.value = response.body()
                } else {
                    _registrationResult.value = null
                }
            } catch (e: Exception) {
                _registrationResult.value = null
            }
        }
    }

    private fun enableRegistroEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun enableRegistroPassword(password: String): Boolean {
        return password.length >= 8
    }

    private fun enableRegisto(nombre: String, email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && nombre.length <= 50
    }

    private fun enableCodigo(codigo: String): Boolean {
        return true
    }

    private fun enableArrobaName(arrobaNombre: String): Boolean {
        return arrobaNombre.length <= 20
    }
}