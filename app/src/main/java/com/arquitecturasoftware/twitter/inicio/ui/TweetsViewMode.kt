package com.arquitecturasoftware.twitter.inicio.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arquitecturasoftware.twitter.api.RetrofitHelper
import com.arquitecturasoftware.twitter.api.TokenManager
import com.arquitecturasoftware.twitter.api.response.interactionservice.CommentRequest
import com.arquitecturasoftware.twitter.inicio.ui.model.Comment
import kotlinx.coroutines.launch

class TweetsViewModel : ViewModel() {
    private val _tweets = MutableLiveData<List<Tweet>>()
    val tweets: LiveData<List<Tweet>> get() = _tweets

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> get() = _comments

    private val _commentCounts = MutableLiveData<Map<Int,Int>>()
    val commentCounts: LiveData<Map<Int,Int>> get() = _commentCounts

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

    fun postComment(commentRequest: CommentRequest, context: Context,tweetId: Int, token: String = "Bearer "+TokenManager.accessToken) {
        if (commentRequest.content.isEmpty()) {
            Toast.makeText(context, "Comentario vacío", Toast.LENGTH_SHORT).show()
            return
        }
        viewModelScope.launch {
            try {
                val response = RetrofitHelper.interactionService.postComment(token,commentRequest)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Comentario publicado", Toast.LENGTH_SHORT).show()
                    fetchComments(tweetId)
                } else {
                    Log.e("TweetsViewModel", "Failed to post comment")
                }
            } catch (e: Exception) {
                Log.e("TweetsViewModel", "Exception: ${e.message}")
            }
        }
    }

    fun fetchComments(tweetId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitHelper.interactionService.getCommentsByTweetID(tweetId)
                if (response.isSuccessful) {
                    val comments = response.body() ?: emptyList()
                    Log.e("TweetsViewModel", "comments: $comments")
                    if (comments.isNotEmpty()) {
                        _comments.value = comments.map { comment ->
                            Comment(
                                comment = comment.comment,
                                user_name = comment.user_name,
                                tweet_id = comment.tweet_id
                            )
                        }
                    } else {
                        _comments.value = emptyList()
                    }
                } else {
                    Log.e("TweetsViewModel", "Failed to fetch comments")
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
                    val count = response.body()?.total_comments ?: 0
                    _commentCounts.value = _commentCounts.value.orEmpty() + (tweetId to count)
                } else {
                    Log.e("TweetsViewModel", "Failed to fetch comment count")
                }
            } catch (e: Exception) {
                Log.e("TweetsViewModel", "Exception: ${e.message}")
            }
        }
    }
}
