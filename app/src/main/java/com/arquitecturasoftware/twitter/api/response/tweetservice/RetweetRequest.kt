package com.arquitecturasoftware.twitter.api.response.tweetservice

import com.google.gson.annotations.SerializedName

data class RetweetRequest(
    @SerializedName("tweet_id") val tweet_id: Int
)
