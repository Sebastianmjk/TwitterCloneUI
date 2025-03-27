package com.arquitecturasoftware.twitter.inicio.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arquitecturasoftware.twitter.api.RetrofitHelper
import kotlinx.coroutines.launch

class TweetsViewModel : ViewModel() {
    private val _tweets = MutableLiveData<List<Tweet>>()
    val tweets: LiveData<List<Tweet>> get() = _tweets

    fun fetchTweets() {
        viewModelScope.launch {
            try {
                val response = RetrofitHelper.interactionService.getAllTweets()
                if (response.isSuccessful) {
                    Log.e("TweetsViewModel", "response: ${response.body()}")
                    _tweets.value = response.body() ?: emptyList()
                } else {
                    Log.e("TweetsViewModel", "response: ${response.body()}")
                    Log.e("TweetsViewModel", "Failed to fetch tweets")
                }
            } catch (e: Exception) {
                Log.e("TweetsViewModel", "Exception: ${e.message}")
            }
        }
    }
}
