package com.arquitecturasoftware.twitter.api.response.interactionservice

import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("user_name") val user_name: String,
    @SerializedName("tweet_id") val tweet_id: Int,
    @SerializedName("comment") val comment: String
)
