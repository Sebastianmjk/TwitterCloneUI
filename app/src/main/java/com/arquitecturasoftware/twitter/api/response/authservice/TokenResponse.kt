package com.arquitecturasoftware.twitter.api.response.authservice

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("access_token") val accessToken: String
)
