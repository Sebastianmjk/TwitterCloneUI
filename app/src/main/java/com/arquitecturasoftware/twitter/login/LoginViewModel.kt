package com.arquitecturasoftware.twitter.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) :ViewModel() {

    private val _nombre = MutableLiveData<String>()
    val nombre : LiveData<String> = _nombre

    private val _fechaNacimiento = MutableLiveData<String>()
    val fechaNacimiento : LiveData<String> = _fechaNacimiento

    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _isLoginEnable = MutableLiveData<Boolean>()
    val isLoginEnable : LiveData<Boolean> = _isLoginEnable

    private val _isRegisterEnable = MutableLiveData<Boolean>()
    val isRegisterEnable : LiveData<Boolean> = _isRegisterEnable

    private val _isLoginEnablePassword = MutableLiveData<Boolean>()
    val isLoginEnablePassword : LiveData<Boolean> = _isLoginEnablePassword

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun onLoginChangesEmail(email:String){
        _email.value = email
        _isLoginEnable.value = enableLoginEmail(email)
    }

    fun onLoginChangesPassword(password:String){
        _password.value = password
        _isLoginEnablePassword.value = enableLoginPassword(password)
    }

    fun onRegistroChanges(nombre:String, email:String, fechaNacimiento:String){
        _nombre.value = nombre
        _email.value = email
        _fechaNacimiento.value = fechaNacimiento
        _isRegisterEnable.value = enableRegisto(nombre, email, fechaNacimiento)
    }

    private fun enableRegisto(nombre: String, email: String, fechaNacimiento: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && nombre.length <= 50 && fechaNacimiento.isNotEmpty()
    }

    private fun enableLoginEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun enableLoginPassword(password: String): Boolean {
        return password.length >= 6
    }

}