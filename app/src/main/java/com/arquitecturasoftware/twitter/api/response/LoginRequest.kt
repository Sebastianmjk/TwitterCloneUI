package com.arquitecturasoftware.twitter.api.response

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("grant_type") val grantType: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("scope") val scope: String?,
    @SerializedName("client_id") val clientId: String?,
    @SerializedName("client_secret") val clientSecret: String?
)
