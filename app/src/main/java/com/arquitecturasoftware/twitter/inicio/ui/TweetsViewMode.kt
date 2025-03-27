package com.arquitecturasoftware.twitter.inicio.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arquitecturasoftware.twitter.api.RetrofitHelper
import com.arquitecturasoftware.twitter.api.response.interactionservice.CommentRequest
import kotlinx.coroutines.launch

class TweetsViewModel : ViewModel() {
    private val _tweets = MutableLiveData<List<Tweet>>()
    val tweets: LiveData<List<Tweet>> get() = _tweets

    private val _commentCount = MutableLiveData<Int>()
    val commentCount: LiveData<Int> get() = _commentCount

    fun fetchTweets() {
        viewModelScope.launch {
            try {
                val response = RetrofitHelper.tweetService.getAllTweets()
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
    fun postComment(commentRequest: CommentRequest) {
        viewModelScope.launch {
            try {
                val response = RetrofitHelper.interactionService.postComment(commentRequest)
                if (response.isSuccessful) {
                    // Handle successful comment post
                } else {
                    Log.e("TweetsViewModel", "Failed to post comment")
                }
            } catch (e: Exception) {
                Log.e("TweetsViewModel", "Exception: ${e.message}")
            }
        }
    }

    fun fetchCommentCount(tweetId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitHelper.interactionService.getCountCommentsByTweetID(tweetId)
                if (response.isSuccessful) {
                    _commentCount.value = response.body()?.total_comments ?: 0
                } else {
                    Log.e("TweetsViewModel", "Failed to fetch comment count")
                }
            } catch (e: Exception) {
                Log.e("TweetsViewModel", "Exception: ${e.message}")
            }
        }
    }
}
