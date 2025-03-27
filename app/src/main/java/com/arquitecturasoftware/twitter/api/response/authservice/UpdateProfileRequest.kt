package com.arquitecturasoftware.twitter.api.response.authservice

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("name") val name: String,
    //@SerializedName("full_name") val fullName: String,
    @SerializedName("password") val password: String,
    //@SerializedName("biography") val biography: String

)