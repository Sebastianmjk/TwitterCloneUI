package com.arquitecturasoftware.twitter.api.response.interactionservice

import com.arquitecturasoftware.twitter.inicio.ui.Tweet

data class LikesResponse(
    val id: Int,
    val content: String?,
    val created_at: String?,
    val user_name: String?
)
{
    fun toTweet(): Tweet {
        return Tweet(
            id = this.id,
            user_name = this.user_name ?: "Unknown",
            content = this.content ?: "",
            created_at = this.created_at ?: ""
        )
    }
}