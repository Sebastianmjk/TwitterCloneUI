package com.arquitecturasoftware.twitter.api.response.interactionservice

import com.google.gson.annotations.SerializedName

data class CommentRequest(
    @SerializedName("tweet_id") val tweet_id: Int,
    @SerializedName("content") val content: String,
)
