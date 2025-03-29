package com.arquitecturasoftware.twitter.api.response.authservice

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("message") val message: String
)
