package com.arquitecturasoftware.twitter.api.response.tweetservice

import com.google.gson.annotations.SerializedName

data class TweetRequest (
    @SerializedName("content") val content: String
)