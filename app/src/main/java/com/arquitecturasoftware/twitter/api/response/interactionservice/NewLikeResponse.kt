package com.arquitecturasoftware.twitter.api.response.interactionservice

import com.google.gson.annotations.SerializedName

data class NewLikeResponse(
    @SerializedName("user_name") val user_name: String,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("tweet_id") val tweet_id: Int,
)
