package com.arquitecturasoftware.twitter.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _selectedIndex = MutableLiveData(0)
    val selectedIndex: LiveData<Int> get() = _selectedIndex

    fun setSelectedIndex(index: Int) {
        _selectedIndex.value = index
    }
}