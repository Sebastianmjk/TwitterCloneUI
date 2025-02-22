package com.arquitecturasoftware.twitter.inicio.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.arquitecturasoftware.twitter.R
import com.arquitecturasoftware.twitter.api.TokenManager
import com.arquitecturasoftware.twitter.routes.Routes
import kotlinx.coroutines.launch

@Composable
fun AddTweet(navigationController: NavHostController, addTweetViewModel: AddTweetViewModel) {
    val content: String by addTweetViewModel.content.observeAsState("")
    val addResult: String by addTweetViewModel.addResult.observeAsState("")
    val token = "Bearer "+TokenManager.accessToken
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        HeaderAddTweet(Modifier.align(Alignment.Start), navigationController) {
            coroutineScope.launch {
                val success = addTweetViewModel.addTweet(
                    content = content,
                    token = token
                )
                if (success) {
                    navigationController.navigate(Routes.Inicio.ruta)
                } else {
                    Log.i("isError", "Error")
                    Toast.makeText(
                        navigationController.context,
                        "Tweet failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        Spacer(modifier = Modifier.size(4.dp))
        Tweetear(Modifier.align(Alignment.CenterHorizontally), addTweetViewModel, content)
    }
}

@Composable
fun Tweetear(modifier: Modifier, addTweetViewModel: AddTweetViewModel,content:String) {
    Row(modifier.fillMaxWidth().padding(16.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "profile picture",
            modifier = Modifier.clip(shape = CircleShape).size(55.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        TextField(
            value = content,
            onValueChange = { addTweetViewModel.updateContent(it) },
            maxLines = 12,
            label = { Text("¿Qué está pensando?") },
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                unfocusedTextColor = Color.LightGray,
                focusedTextColor = Color.Black
            )
        )
    }
}

@Composable
fun HeaderAddTweet(modifier: Modifier = Modifier, navController: NavController,onClick:()->Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close app",
            modifier = Modifier
                .clickable { navController.popBackStack() },
            tint = Color.White
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {onClick()},
            modifier = Modifier
                .weight(1f)
        ) {
            Text("Publicar")
        }
    }
}