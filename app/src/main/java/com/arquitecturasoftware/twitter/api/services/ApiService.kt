package com.arquitecturasoftware.twitter.api.services

import com.arquitecturasoftware.twitter.api.response.authservice.UpdateProfileRequest
import com.arquitecturasoftware.twitter.api.response.ProfilePhotoResponse
import com.arquitecturasoftware.twitter.api.response.interactionservice.TweetRequest
import com.arquitecturasoftware.twitter.api.response.interactionservice.TweetResponse
import com.arquitecturasoftware.twitter.api.response.authservice.UsersProfileResponse
import com.arquitecturasoftware.twitter.api.response.UsersTweetLikedRetweetedResponse
import com.arquitecturasoftware.twitter.inicio.ui.Tweet
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("/users/")
    suspend fun getUsers(): Response<UsersProfileResponse>

    @GET("/profile/")
    suspend fun getProfile(@Header("Authorization") token: String): Response<UsersProfileResponse>

    @GET("/users/tweets/liked")
    suspend fun getLikedTweets(): Response<UsersTweetLikedRetweetedResponse>

    @GET("/users/tweets/retweeted")
    suspend fun getRetweetedTweets(): Response<UsersTweetLikedRetweetedResponse>

    @GET("/tweets/{tweet_id}/likes/count")
    suspend fun getLikesCount(@Path("tweet_id") tweetId: Int): Response<String>

    @GET("/tweets/{tweet_id}/retweets/count")
    suspend fun getRetweetsCount(@Path("tweet_id") tweetId: Int): Response<String>

    @GET("/user/me/profile_photo")
    suspend fun getProfilePhoto(): Response<ProfilePhotoResponse>

    @POST("/user/me/profile_photo")
    suspend fun postProfilePhoto(): Response<ProfilePhotoResponse>


    @PUT("/profile/")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body updateProfileRequest: UpdateProfileRequest
    ): Response<UsersProfileResponse>
}

