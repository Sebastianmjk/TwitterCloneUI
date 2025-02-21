package com.arquitecturasoftware.twitter.registro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.util.Patterns
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistroViewModel @Inject constructor() : ViewModel() {

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

    fun onRegistroChanges(nombre:String, email:String, fechaNacimiento:String){
        _nombre.value = nombre
        _email.value = email
        _fechaNacimiento.value = fechaNacimiento
        _isRegisterEnable.value = enableRegisto(nombre, email, fechaNacimiento)
    }

    private fun enableRegistroEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun enableRegistroPassword(password: String): Boolean {
        return password.length >= 8
    }

    private fun enableRegisto(nombre: String, email: String, fechaNacimiento: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && nombre.length <= 50 && fechaNacimiento.isNotEmpty()
    }

    private fun enableCodigo(codigo: String): Boolean {
        return true
    }

    private fun enableArrobaName(arrobaNombre: String): Boolean {
        return arrobaNombre.length <= 20
    }
}