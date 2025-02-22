package com.arquitecturasoftware.twitter.inicio.ui

data class Tweet(
    val id: Int,
    val user_id: Int,
    val contenido: String
)

data class UsersTweetLikedRetweetedResponse(
    val tweets: List<Tweet>
)