package com.arquitecturasoftware.twitter.api.response.interactionservice

import com.google.gson.annotations.SerializedName

data class CountCommentResponse(
    @SerializedName("total_comments") val total_comments: Int,
)
