package com.arquitecturasoftware.twitter.api.response

import com.google.gson.annotations.SerializedName

data class UsersProfileResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("biography") val biography: String
)