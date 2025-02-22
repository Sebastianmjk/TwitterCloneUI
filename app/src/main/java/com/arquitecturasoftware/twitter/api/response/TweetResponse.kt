package com.arquitecturasoftware.twitter.api.response

import com.google.gson.annotations.SerializedName

data class TweetResponse(
    @SerializedName("message") val message: String,

)