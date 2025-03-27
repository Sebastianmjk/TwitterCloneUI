package com.arquitecturasoftware.twitter.api.response.interactionservice

import com.google.gson.annotations.SerializedName

data class LikeRequest(
    @SerializedName("tweet_id") val tweet_id: Int
)
