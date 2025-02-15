package com.arquitecturasoftware.twitter.inicio.ui

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class InicioViewModel @Inject constructor(): ViewModel() {
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: MutableLiveData<Boolean>  = _showDialog

    fun onDialogClosed(){
        _showDialog.value = false
    }

    fun onAddTweet(tweet:String){
        _showDialog.value = false
    }

    fun onDialogOpened() {
        _showDialog.value = true
    }
}
