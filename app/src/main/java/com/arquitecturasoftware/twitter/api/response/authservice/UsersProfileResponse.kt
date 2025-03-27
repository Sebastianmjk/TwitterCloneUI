package com.arquitecturasoftware.twitter.api.response.authservice

import com.google.gson.annotations.SerializedName

data class UsersProfileResponse(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("biography") val biography: String
)