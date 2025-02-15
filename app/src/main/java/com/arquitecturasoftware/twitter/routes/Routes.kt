package com.arquitecturasoftware.twitter.routes

sealed class Routes(val ruta : String) {
    data object Inicio : Routes("inicio")
    data object AddTweet : Routes("addtweet")
}