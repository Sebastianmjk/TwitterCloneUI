package com.arquitecturasoftware.twitter.api.response.tweetservice

import com.google.gson.annotations.SerializedName

data class RetweetCount(
    @SerializedName("total_retweets") val total_retweets: Int,
)
