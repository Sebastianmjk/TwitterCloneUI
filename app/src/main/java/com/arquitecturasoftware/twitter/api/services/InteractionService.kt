package com.arquitecturasoftware.twitter.api.services

import com.arquitecturasoftware.twitter.api.response.interactionservice.CommentRequest
import com.arquitecturasoftware.twitter.api.response.interactionservice.CommentResponse
import com.arquitecturasoftware.twitter.api.response.interactionservice.CountCommentResponse
import com.arquitecturasoftware.twitter.api.response.interactionservice.LikeResponse
import com.arquitecturasoftware.twitter.api.response.interactionservice.LikesResponse
import com.arquitecturasoftware.twitter.api.response.interactionservice.NewLikeResponse
import com.arquitecturasoftware.twitter.api.response.tweetservice.RetweetRequest
import com.arquitecturasoftware.twitter.api.response.tweetservice.TweetRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface InteractionService {

    //like
    @GET("/service_interaction/likes/tweet/{tweet_id}/count")
    suspend fun getCountLikesByTweetID(@Path("tweet_id") tweetId: Int): Response<LikeResponse>

    @POST("/service_interaction/like")
    suspend fun postLike(@Header("Authorization") token: String,
                         @Body likeResponse: RetweetRequest
    ): Response<NewLikeResponse>

    @DELETE("/service_interaction/like/{tweet_id}")
    suspend fun deleteLike(@Header("Authorization") token: String,
                                   @Path("tweet_id") tweetId: Int): Response<Unit>

    @GET("/service_interaction/likes/user/{user_id}")
    suspend fun getLikesByUser(@Path("user_id") userId: Int): Response<List<LikesResponse>>

    //comment
    @GET("/service_interaction/comments/tweet/{tweet_id}")
    suspend fun getCommentsByTweetID(@Path("tweet_id") tweetId: Int): Response<List<CommentResponse>>

    @GET("/service_interaction/comments/tweet/{tweet_id}/count")
    suspend fun getCountCommentsByTweetID(@Path("tweet_id") tweetId: Int): Response<CountCommentResponse>

    @POST("/service_interaction/comment")
    suspend fun postComment(
        @Header("Authorization") token: String,
        @Body commentRequest: CommentRequest
    ): Response<CommentResponse>

}