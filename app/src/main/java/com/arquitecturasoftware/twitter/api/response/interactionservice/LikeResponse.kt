package com.arquitecturasoftware.twitter.api.response.interactionservice

import com.google.gson.annotations.SerializedName

data class LikeResponse(
    @SerializedName("total_likes") val total_likes: Int
)
