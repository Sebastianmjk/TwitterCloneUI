package com.arquitecturasoftware.twitter.api.response.authservice

import com.google.gson.annotations.SerializedName

data class VerifyTokenResponse(
    @SerializedName("uid") val uid: Int,
    @SerializedName("email") val email: String,
    @SerializedName("full_name") val full_name: String
)
