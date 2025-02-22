package com.arquitecturasoftware.twitter.api.response

import com.google.gson.annotations.SerializedName

data class ProfilePhotoResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("file_path") val filePath: String,
    @SerializedName("file_name") val fileName: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("user_id") val userId: Int)
