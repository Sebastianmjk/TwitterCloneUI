package com.arquitecturasoftware.twitter.api.response.interactionservice

import com.google.gson.annotations.SerializedName

data class TweetsRetweetsResponse(
    @SerializedName("tweets") val tweets: List<TweetResponse>,
    @SerializedName("retweets") val retweets: List<RetweetResponse>
)
