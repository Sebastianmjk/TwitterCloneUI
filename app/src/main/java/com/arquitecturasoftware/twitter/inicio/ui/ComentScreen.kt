package com.arquitecturasoftware.twitter.inicio.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.arquitecturasoftware.twitter.inicio.ui.model.Comentario
import kotlinx.coroutines.launch

@Composable
fun ComentScreen(navController: NavController) {
    val commentText = remember { mutableStateOf("") }
    val comments = remember { mutableStateListOf<Comentario>() }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        HeaderComentario(navController)
        Spacer(modifier = Modifier.size(16.dp))
        ComentarioTextField(commentText)
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = {
                scope.launch {
                    comments.add(Comentario(
                        text = commentText.value,
                        author = "User",
                        profilePictureUrl = "https://example.com/profile.jpg",
                        timestamp = "2h ago"
                    ))
                    commentText.value = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Comentar")
        }
        Spacer(modifier = Modifier.size(16.dp))
        HorizontalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))
        LazyColumn {
            items(comments) { comment ->
                CommentItem(comment)
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comentario) {
    val liked = remember { mutableStateOf(comment.liked) }

    Column(modifier = Modifier.padding(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberImagePainter(data = comment.profilePictureUrl),
                contentDescription = "profile picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = comment.author, color = Color.White)
                Text(text = comment.timestamp, color = Color.Gray, fontSize = 12.sp)
            }
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Like",
                tint = if (liked.value) Color.Red else Color.Gray,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        liked.value = !liked.value
                        comment.liked = liked.value
                    }
            )
        }
        Text(text = comment.text, color = Color.White)
        HorizontalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun HeaderComentario(navController: NavController) {
    Row(
        modifier = Modifier
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
    }
}

@Composable
fun ComentarioTextField(commentText: MutableState<String>) {
    TextField(
        value = commentText.value,
        onValueChange = { commentText.value = it },
        maxLines = 10,
        label = { Text("Comentar") },
        modifier = Modifier.fillMaxWidth(),
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