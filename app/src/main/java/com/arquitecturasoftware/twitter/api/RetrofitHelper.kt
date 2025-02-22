package com.arquitecturasoftware.twitter.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper{
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://localhost")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}