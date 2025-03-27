package com.arquitecturasoftware.twitter.api.services

import com.arquitecturasoftware.twitter.api.response.authservice.RegisterRequest
import com.arquitecturasoftware.twitter.api.response.authservice.TokenResponse
import com.arquitecturasoftware.twitter.api.response.authservice.UpdateProfileRequest
import com.arquitecturasoftware.twitter.api.response.authservice.UsersProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthService {
    // Auth
    @POST("/service_auth/auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<UsersProfileResponse>

    @FormUrlEncoded
    @POST("/service_auth/auth/login")
    suspend fun getLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<TokenResponse>

    @POST("/service_auth/auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<String>

    @GET("/service_user/profile/")
    suspend fun getProfile(@Header("Authorization") token: String): Response<UsersProfileResponse>

    @PUT("/service_user/profile/")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body updateProfileRequest: UpdateProfileRequest
    ): Response<UsersProfileResponse>
}