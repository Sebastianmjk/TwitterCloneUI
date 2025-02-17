package com.arquitecturasoftware.twitter.inicio.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AddTweetViewModel @Inject constructor(): ViewModel() {
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: MutableLiveData<Boolean>  = _showDialog

    fun onAddTweet(tweet:String){
        _showDialog.value = false
    }

}
