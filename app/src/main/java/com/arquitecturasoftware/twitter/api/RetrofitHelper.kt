package com.arquitecturasoftware.twitter.api

import com.arquitecturasoftware.twitter.api.services.ApiService
import com.arquitecturasoftware.twitter.api.services.AuthService
import com.arquitecturasoftware.twitter.api.services.InteractionService
import com.arquitecturasoftware.twitter.api.services.TweetService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper{
    private const val BASE_URL = "http://192.168.1.18:8000"
    private val retrofit by lazy {
        Retrofit.Builder()
            //.baseUrl("http://192.168.1.36:8000")
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    val authApi: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val tweetService: TweetService by lazy {
        retrofit.create(TweetService::class.java)
    }

    val interactionService: InteractionService by lazy {
        retrofit.create(InteractionService::class.java)
    }
}