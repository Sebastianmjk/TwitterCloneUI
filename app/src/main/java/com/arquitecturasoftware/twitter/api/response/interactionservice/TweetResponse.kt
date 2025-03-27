package com.arquitecturasoftware.twitter.api.response.interactionservice

import com.google.gson.annotations.SerializedName

data class TweetResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("user_name") val user_name: String,
    @SerializedName("content") val content: String,
    @SerializedName("created_at") val created_at: String

)