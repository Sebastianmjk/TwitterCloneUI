package com.arquitecturasoftware.twitter.api.response

import com.google.gson.annotations.SerializedName

data class TweetRequest (
    @SerializedName("content") val content: String
)