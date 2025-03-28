// src/main/java/com/arquitecturasoftware/twitter/api/response/tweetservice/TweetResponse.kt
package com.arquitecturasoftware.twitter.api.response.tweetservice

import com.google.gson.annotations.SerializedName
import com.arquitecturasoftware.twitter.inicio.ui.Tweet

data class TweetResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("author_name") val user_name: String?,
    @SerializedName("content") val content: String?,
    @SerializedName("created_at") val created_at: String?
) {
    fun toTweet(): Tweet {
        return Tweet(
            id = this.id,
            user_name = this.user_name ?: "Unknown", // Valor predeterminado si user_name es nulo
            content = this.content ?: "", // Valor predeterminado si content es nulo
            created_at = this.created_at ?: "" // Valor predeterminado si created_at es nulo
        )
    }
}