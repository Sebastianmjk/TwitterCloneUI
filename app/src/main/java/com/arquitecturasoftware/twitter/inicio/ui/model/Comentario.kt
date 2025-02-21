package com.arquitecturasoftware.twitter.inicio.ui.model

data class Comentario(
    val text: String,
    val author: String,
    val profilePictureUrl: String,
    val timestamp: String,
    var liked: Boolean = false
)