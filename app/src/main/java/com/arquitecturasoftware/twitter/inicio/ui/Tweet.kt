package com.arquitecturasoftware.twitter.inicio.ui

data class Tweet(
    val id: Int,
    val user_name: String,
    val content: String,
    val created_at: String
)

data class UsersTweetLikedRetweetedResponse(
    val tweets: List<Tweet>
)