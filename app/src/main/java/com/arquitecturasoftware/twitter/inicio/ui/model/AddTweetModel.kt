package com.arquitecturasoftware.twitter.inicio.ui.model

data class AddTweetModel(val id:Long = System.currentTimeMillis(), val tweet:String)
