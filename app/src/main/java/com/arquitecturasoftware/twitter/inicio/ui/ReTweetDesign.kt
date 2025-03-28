package com.arquitecturasoftware.twitter.inicio.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arquitecturasoftware.twitter.R

@Composable
fun ReTweetDesign(navController: NavController,tweetsViewModel: TweetsViewModel, retweeter: String, tweet: Tweet) {
    Column(modifier = Modifier.padding(10.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.ic_rt),
            contentDescription = "retweet icon",
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = "$retweeter ha retweeteado",
            color = Color.Gray,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
    TweetDesign(navController, tweet, tweetsViewModel)
}