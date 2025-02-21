package com.arquitecturasoftware.twitter.inicio.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arquitecturasoftware.twitter.R
import com.arquitecturasoftware.twitter.routes.Routes

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold( containerColor = Color.Black,
        topBar = {
            HeaderProfile()
        },
        bottomBar = {
            MyBottomNavigationInicio(navController)
        },
        floatingActionButton = {
            Fab(onAbrirMenu = { navController.navigate(Routes.AddTweet.ruta) })
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Row{
                var selectedButton by remember { mutableStateOf(SelectedButton.NONE) }
                TextButton(
                    onClick = { selectedButton = SelectedButton.PUBLICACIONES },
                    colors = ButtonDefaults.textButtonColors(disabledContentColor = Color.Gray, contentColor = Color.White),
                    border = if (selectedButton == SelectedButton.PUBLICACIONES) BorderStroke(1.dp, Color(0xFFADD8E6)) else BorderStroke(1.dp, Color.Transparent)
                ) {
                    Text(text = "Publicaciones")
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = { selectedButton = SelectedButton.RETWEETS },
                    colors = ButtonDefaults.textButtonColors(disabledContentColor = Color.Gray, contentColor = Color.White),
                    border = if (selectedButton == SelectedButton.RETWEETS) BorderStroke(1.dp, Color(0xFFADD8E6)) else BorderStroke(1.dp, Color.Transparent)
                ) {
                    Text(text = "Retweets")
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = { selectedButton = SelectedButton.ME_GUSTA },
                    colors = ButtonDefaults.textButtonColors(disabledContentColor = Color.Gray, contentColor = Color.White),
                    border = if (selectedButton == SelectedButton.ME_GUSTA) BorderStroke(1.dp, Color(0xFFADD8E6)) else BorderStroke(1.dp, Color.Transparent)
                ) {
                    Text(text = "Me gusta")
                }
            }
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)
            LazyColumn {
                items(3) {
                    TweetDesign()
                }
            }
        }
    }
}

enum class SelectedButton {
    PUBLICACIONES, RETWEETS, ME_GUSTA, NONE
}

@Composable
fun HeaderProfile(){
    Column (Modifier.fillMaxWidth()){
        Row (Modifier.fillMaxWidth().padding(top = 26.dp, start = 16.dp, end = 16.dp)){
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "profile picture",
                modifier = Modifier.clip(shape = CircleShape).size(55.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { }, border = BorderStroke(1.dp, Color.Gray), colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color.Black)) {
                Text("Configurar Perfil")
            }
        }
        Text("AristiDevs", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp, start = 16.dp))
        Text("@AristiDevs", color = Color.Gray, fontSize = 16.sp, modifier = Modifier.padding(start = 16.dp))
    }
}