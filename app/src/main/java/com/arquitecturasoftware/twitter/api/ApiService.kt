package com.arquitecturasoftware.twitter.api

import com.arquitecturasoftware.twitter.api.response.LoginRequest
import com.arquitecturasoftware.twitter.api.response.ProfilePhotoResponse
import com.arquitecturasoftware.twitter.api.response.RegisterRequest
import com.arquitecturasoftware.twitter.api.response.TokenResponse
import com.arquitecturasoftware.twitter.api.response.TweetRequest
import com.arquitecturasoftware.twitter.api.response.TweetResponse
import com.arquitecturasoftware.twitter.api.response.UsersProfileResponse
import com.arquitecturasoftware.twitter.api.response.UsersTweetLikedRetweetedResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    // Default

    @GET("/users/")
    suspend fun getUsers(): Response<UsersProfileResponse>

    @GET("/profile/")
    suspend fun getProfile(): Response<UsersProfileResponse>

    @GET("/users/tweets/liked")
    suspend fun getLikedTweets(): Response<UsersTweetLikedRetweetedResponse>

    @GET("/users/tweets/retweeted")
    suspend fun getRetweetedTweets(): Response<UsersTweetLikedRetweetedResponse>

    @POST("/logout")
    suspend fun logout(@Header("Authorization") authToken: String): Response<String>

    // Auth
    @FormUrlEncoded
    @POST("/login")
    suspend fun getLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<TokenResponse>

    @POST("/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<UsersProfileResponse>

    @GET("/tweets/{tweet_id}")
    suspend fun getTweetById(@Path("tweet_id") tweetId: Int): Response<UsersTweetLikedRetweetedResponse>

    @GET("/tweets/")
    suspend fun getTweets(): Response<UsersTweetLikedRetweetedResponse>

    @GET("/tweets/{tweet_id}/likes/count")
    suspend fun getLikesCount(@Path("tweet_id") tweetId: Int): Response<String>

    @GET("/tweets/{tweet_id}/retweets/count")
    suspend fun getRetweetsCount(@Path("tweet_id") tweetId: Int): Response<String>

    @GET("/user/me/profile_photo")
    suspend fun getProfilePhoto(): Response<ProfilePhotoResponse>

    @Multipart
    @POST("/user/me/profile_photo")
    suspend fun postProfilePhoto(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Response<ProfilePhotoResponse>

    @POST("/tweet/")
    suspend fun postTweet(
        @Header("Authorization") token: String,
        @Body tweetRequest: TweetRequest
    ): Response<TweetResponse>

    @PUT("/profile/")
    suspend fun updateProfile(
        @Body profileUpdateRequest: ProfileUpdateRequest
    ): Response<UsersProfileResponse>
}

