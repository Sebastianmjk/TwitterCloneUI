package com.arquitecturasoftware.twitter.api.response.tweetservice

import com.google.gson.annotations.SerializedName

data class RetweetResponse(
    @SerializedName("retweeter_name") val retweeter_name: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("tweet") val tweet: TweetResponse
)
