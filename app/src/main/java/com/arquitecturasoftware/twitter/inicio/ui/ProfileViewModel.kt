package com.arquitecturasoftware.twitter.inicio.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arquitecturasoftware.twitter.api.RetrofitHelper
import com.arquitecturasoftware.twitter.api.response.authservice.UpdateProfileRequest
import com.arquitecturasoftware.twitter.api.response.authservice.UsersProfileResponse
import com.arquitecturasoftware.twitter.api.response.authservice.VerifyTokenResponse
import com.arquitecturasoftware.twitter.api.response.tweetservice.RetweetRequest
import com.arquitecturasoftware.twitter.api.response.tweetservice.RetweetResponse
import com.arquitecturasoftware.twitter.api.response.tweetservice.TweetsRetweetsResponse
import com.arquitecturasoftware.twitter.api.services.AuthService
import com.arquitecturasoftware.twitter.api.services.TweetService
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val authService: AuthService = RetrofitHelper.authApi
    private val tweetService: TweetService = RetrofitHelper.tweetService

    private val _profileUpdateResult = MutableLiveData<UsersProfileResponse?>()
    val profileUpdateResult: LiveData<UsersProfileResponse?> = _profileUpdateResult

    suspend fun updateProfile(token: String, name: String, password: String): Boolean {
        return try {
            val request = UpdateProfileRequest(name, password)
            val response = authService.updateProfile(token, request)
            if (response.isSuccessful) {
                _profileUpdateResult.value = response.body()
                Log.i("ProfileViewModel", "Response: ${response.body()}")
                true
            } else {
                _profileUpdateResult.value = null
                Log.i("ProfileViewModel", "Error: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            _profileUpdateResult.value = null
            Log.e("ProfileViewModel", "Exception: ${e.message}")
            false
        }
    }

    private val _profile = MutableLiveData<UsersProfileResponse?>()
    val profile: LiveData<UsersProfileResponse?> = _profile

    private val _tokenData = MutableLiveData<VerifyTokenResponse?>()
    val tokenData: LiveData<VerifyTokenResponse?> = _tokenData

    private val _userTweets = MutableLiveData<List<Tweet>?>()
    val userTweets: LiveData<List<Tweet>?> = _userTweets

    private val _userRetweets = MutableLiveData<List<RetweetResponse>?>()
    val userRetweets: LiveData<List<RetweetResponse>?> = _userRetweets

    fun fetchProfile(token: String) {
        viewModelScope.launch {
            try {
                val response = authService.getProfile(token)
                if (response.isSuccessful) {
                    _profile.value = response.body()
                } else {
                    _profile.value = null
                    Log.e("ProfileViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _profile.value = null
                Log.e("ProfileViewModel", "Exception: ${e.message}")
            }
        }
    }

    fun fetchTokenData(token: String) {
        viewModelScope.launch {
            try {
                val response = authService.verifyToken(token)
                if (response.isSuccessful) {
                    _tokenData.value = response.body()
                } else {
                    _tokenData.value = null
                    Log.e("ProfileViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _tokenData.value = null
                Log.e("ProfileViewModel", "Exception: ${e.message}")
            }
        }
    }

    fun fetchUserTweets(userId: Int) {
        viewModelScope.launch {
            try {
                val response = tweetService.getTweetsByUser(userId)
                if (response.isSuccessful) {
                    _userTweets.value = response.body()
                } else {
                    _userTweets.value = null
                    Log.e("ProfileViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _userTweets.value = null
                Log.e("ProfileViewModel", "Exception: ${e.message}")
            }
        }
    }

    fun fetchUserRetweets(userId: Int) {
        viewModelScope.launch {
            try {
                val response = tweetService.getRetweetsByUser(userId)
                if (response.isSuccessful) {
                    _userRetweets.value = response.body()
                } else {
                    _userRetweets.value = null
                    Log.e("ProfileViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _userRetweets.value = null
                Log.e("ProfileViewModel", "Exception: ${e.message}")
            }
        }
    }

    var retweetedTweets = mutableStateOf(mutableSetOf<Int>())
        private set

    var retweetCounts = mutableStateOf(mutableMapOf<Int, Int>())
        private set

    var chattedTweets = mutableStateOf(mutableSetOf<Int>())
        private set

    var chatCounts = mutableStateOf(mutableMapOf<Int, Int>())
        private set

    var likedTweets = mutableStateOf(mutableSetOf<Int>())
        private set

    var likeCounts = mutableStateOf(mutableMapOf<Int, Int>())
        private set

    fun toggleRetweet(token: String, tweetId: Int) {
        val retweeted = retweetedTweets.value.toMutableSet()
        val counts = retweetCounts.value.toMutableMap()
        if (retweeted.contains(tweetId)) {
            retweeted.remove(tweetId)
            counts[tweetId] = (counts[tweetId] ?: 1) - 1
            Log.d("ProfileViewModel", "Calling deleteRtweet for tweetId: $tweetId")
            deleteRtweet(token, tweetId)
        } else {
            retweeted.add(tweetId)
            counts[tweetId] = (counts[tweetId] ?: 0) + 1
            Log.d("ProfileViewModel", "Calling postRetweet for tweetId: $tweetId")
            postRetweet(token, tweetId)
        }
        retweetedTweets.value = retweeted
        retweetCounts.value = counts
    }

    private fun postRetweet(token: String, tweetId: Int) {
        viewModelScope.launch {
            try {
                val response = tweetService.postRetweet(token, RetweetRequest(tweetId))
                if (response.isSuccessful) {
                    Log.i("ProfileViewModel", "Retweet successful: ${response.body()}")
                } else {
                    Log.e("ProfileViewModel", "Error posting retweet: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Exception: ${e.message}")
            }
        }
    }

    private fun deleteRtweet(token: String, tweetId: Int) {
        viewModelScope.launch {
            try {
                val response = tweetService.deleteRtweet(token, tweetId)
                if (response.isSuccessful) {
                    Log.i("ProfileViewModel", "Retweet deleted successfully")
                } else {
                    Log.e("ProfileViewModel", "Error deleting retweet: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Exception: ${e.message}")
            }
        }
    }

    fun toggleChat(tweetId: Int) {
        val chatted = chattedTweets.value.toMutableSet()
        val counts = chatCounts.value.toMutableMap()
        if (chatted.contains(tweetId)) {
            chatted.remove(tweetId)
            counts[tweetId] = (counts[tweetId] ?: 1) - 1
        } else {
            chatted.add(tweetId)
            counts[tweetId] = (counts[tweetId] ?: 0) + 1
        }
        chattedTweets.value = chatted
        chatCounts.value = counts
    }

    fun toggleLike(tweetId: Int) {
        val liked = likedTweets.value.toMutableSet()
        val counts = likeCounts.value.toMutableMap()
        if (liked.contains(tweetId)) {
            liked.remove(tweetId)
            counts[tweetId] = (counts[tweetId] ?: 1) - 1
        } else {
            liked.add(tweetId)
            counts[tweetId] = (counts[tweetId] ?: 0) + 1
        }
        likedTweets.value = liked
        likeCounts.value = counts
    }
}