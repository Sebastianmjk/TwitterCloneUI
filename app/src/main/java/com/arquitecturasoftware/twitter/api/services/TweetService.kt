package com.arquitecturasoftware.twitter.api.services

import com.arquitecturasoftware.twitter.api.response.tweetservice.RetweetRequest
import com.arquitecturasoftware.twitter.api.response.tweetservice.RetweetResponse
import com.arquitecturasoftware.twitter.api.response.tweetservice.TweetRequest
import com.arquitecturasoftware.twitter.api.response.tweetservice.TweetResponse
import com.arquitecturasoftware.twitter.api.response.tweetservice.TweetsRetweetsResponse
import com.arquitecturasoftware.twitter.inicio.ui.Tweet
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface TweetService{
    // tweets
    @GET("/service_tweet/tweets/")
    suspend fun getAllTweets(): Response<List<Tweet>>

    @GET("/service_tweet/tweets/{tweet_id}")
    suspend fun getTweetById(@Path("tweet_id") tweetId: Int): Response<TweetResponse>

    @GET("/service_tweet/tweets/user/{user_id}")
    suspend fun getTweetsByUser(@Path("user_id") userId: Int): Response<List<Tweet>>

    @GET("/service_tweet/tweets/retweets/")
    suspend fun getTweetsAndRetweets(): Response<TweetsRetweetsResponse>

    @GET("/service_tweet/tweets/retweeted/{user_id}")
    suspend fun getRetweetedTweetsByUser(@Path("user_id") userId: Int): Response<List<TweetsRetweetsResponse>>

    @POST("/service_tweet/tweet/")
    suspend fun postTweet(
        @Header("Authorization") token: String,
        @Body tweetRequest: TweetRequest
    ): Response<TweetResponse>

    // retweets
    @GET("/service_tweet/retweets/")
    suspend fun getAllRetweets(): Response<List<RetweetResponse>>

    @GET("/service_tweet/retweets/{tweet_id}")
    suspend fun getRetweetsByTweetId(@Path("tweet_id") tweetId: Int): Response<List<RetweetResponse>>

    @GET("/service_tweet/retweets/user/{user_id}")
    suspend fun getRetweetsByUser(@Path("user_id") userId: Int): Response<List<RetweetResponse>>

    @POST("/service_tweet/retweet/")
    suspend fun postRetweet(
        @Header("Authorization") token: String,
        @Body retweetRequest: RetweetRequest
    ): Response<RetweetResponse>
}