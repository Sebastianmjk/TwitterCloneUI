package com.arquitecturasoftware.twitter.inicio.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arquitecturasoftware.twitter.api.services.ApiService
import com.arquitecturasoftware.twitter.api.RetrofitHelper
import com.arquitecturasoftware.twitter.api.response.interactionservice.TweetRequest
import com.arquitecturasoftware.twitter.api.services.InteractionService

class AddTweetViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _addResult = MutableLiveData<String>()
    val addResult: LiveData<String> = _addResult

    private val interactionService: InteractionService = RetrofitHelper.interactionService

    private val _content = MutableLiveData<String>()
    val content: LiveData<String> = _content

    fun updateContent(newContent: String) {
        _content.value = newContent
    }

    fun prueba(addResult: String) {
        _addResult.value = addResult
    }

    suspend fun addTweet(token: String, content: String): Boolean {
        _isLoading.value = true
        return try {
            val tweetRequest = TweetRequest(content = content)
            val response = interactionService.postTweet(token, tweetRequest)
            if (response.isSuccessful) {
                val tweetResponse = response.body()
                _addResult.value =  "Tweet added successfully"
                true
            } else {
                _addResult.value = "Failed to add tweet"
                false
            }
        } catch (e: Exception) {
            _addResult.value = "Failed to add tweet"
            false
        } finally {
            _isLoading.value = false
        }
    }
}



