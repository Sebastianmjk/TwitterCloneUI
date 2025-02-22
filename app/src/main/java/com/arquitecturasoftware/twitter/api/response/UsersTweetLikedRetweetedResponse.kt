package com.arquitecturasoftware.twitter.api.response

import com.arquitecturasoftware.twitter.inicio.ui.Tweet
import com.google.gson.annotations.SerializedName

data class UsersTweetLikedRetweetedResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("contenido") val contenido: String
) {
    val tweets: List<Tweet>? = emptyList()
}