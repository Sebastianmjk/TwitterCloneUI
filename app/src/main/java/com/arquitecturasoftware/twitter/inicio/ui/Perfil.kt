// src/main/java/com/arquitecturasoftware/twitter/inicio/ui/Perfil.kt
package com.arquitecturasoftware.twitter.inicio.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arquitecturasoftware.twitter.api.TokenManager
import com.arquitecturasoftware.twitter.api.response.authservice.UsersProfileResponse
import com.arquitecturasoftware.twitter.login.LoginViewModel
import com.arquitecturasoftware.twitter.login.SharedViewModel
import com.arquitecturasoftware.twitter.routes.Routes

@Composable
fun ProfileScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel,
    tweetsViewModel: TweetsViewModel
) {
    DisableBackPressHandler()
    val token = "Bearer " + TokenManager.accessToken
    val profile by profileViewModel.profile.observeAsState()
    val tokenData by profileViewModel.tokenData.observeAsState()
    val userTweets by profileViewModel.userTweets.observeAsState(emptyList())
    val userRetweets by profileViewModel.userRetweets.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        profileViewModel.fetchProfile(token)
        profileViewModel.fetchTokenData(token)
    }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            HeaderProfile(navController, profile)
        },
        bottomBar = {
            MyBottomNavigationInicio(navController, sharedViewModel)
        },
        floatingActionButton = {
            Fab(onAbrirMenu = { navController.navigate(Routes.AddTweet.ruta) })
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(bottom = 8.dp)
        ) {
            var selectedButton by remember { mutableStateOf(SelectedButton.PUBLICACIONES) }
            Row {
                TextButton(
                    onClick = { selectedButton = SelectedButton.PUBLICACIONES },
                    colors = ButtonDefaults.textButtonColors(
                        disabledContentColor = Color.Gray,
                        contentColor = Color.White
                    ),
                    border = if (selectedButton == SelectedButton.PUBLICACIONES) BorderStroke(
                        1.dp,
                        Color(0xFFADD8E6)
                    ) else BorderStroke(1.dp, Color.Transparent)
                ) {
                    Text(text = "Publicaciones")
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = { selectedButton = SelectedButton.RETWEETS },
                    colors = ButtonDefaults.textButtonColors(
                        disabledContentColor = Color.Gray,
                        contentColor = Color.White
                    ),
                    border = if (selectedButton == SelectedButton.RETWEETS) BorderStroke(
                        1.dp,
                        Color(0xFFADD8E6)
                    ) else BorderStroke(1.dp, Color.Transparent)
                ) {
                    Text(text = "Retweets")
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = { selectedButton = SelectedButton.ME_GUSTA },
                    colors = ButtonDefaults.textButtonColors(
                        disabledContentColor = Color.Gray,
                        contentColor = Color.White
                    ),
                    border = if (selectedButton == SelectedButton.ME_GUSTA) BorderStroke(
                        1.dp,
                        Color(0xFFADD8E6)
                    ) else BorderStroke(1.dp, Color.Transparent)
                ) {
                    Text(text = "Me gusta")
                }
            }
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)

            tokenData?.let { tokenData ->
                when (selectedButton) {
                    SelectedButton.PUBLICACIONES -> {
                        LaunchedEffect(Unit) {
                            profileViewModel.fetchUserTweets(tokenData.uid)
                        }
                        LazyColumn {
                            items(userTweets ?: emptyList()) { tweet ->
                                TweetDesign(navController, tweet, profileViewModel, tweetsViewModel)
                            }
                        }
                    }
                    SelectedButton.RETWEETS -> {
                        LaunchedEffect(Unit) {
                            profileViewModel.fetchUserRetweets(tokenData.uid)
                        }
                        LazyColumn {
                            items(userRetweets ?: emptyList()) { retweet ->
                                ReTweetDesign(navController, retweet, profileViewModel, tweetsViewModel)
                            }
                        }
                    }
                    SelectedButton.ME_GUSTA -> {
                        LazyColumn {
                            // Implementación para "Me gusta"
                        }
                    }
                }
            }
        }
    }
}

enum class SelectedButton {
    PUBLICACIONES, RETWEETS, ME_GUSTA
}

@Composable
fun HeaderProfile(navController: NavController, profile: UsersProfileResponse?) {

    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 36.dp, start = 16.dp, end = 16.dp)
        ) {
            Button(
                onClick = { navController.navigate(Routes.EditarPerfil.ruta) },
                border = BorderStroke(1.dp, Color.Gray),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Black
                )
            ) {
                Text("Editar Perfil")
            }
            Spacer(modifier = Modifier.weight(1f))
            CerrarSesionButton(navController)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.padding(end = 24.dp)) {
                Text(
                    profile?.name ?: "Loading...",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    "@${profile?.name ?: "Loading..."}",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

        }
    }
}

@Composable
fun CerrarSesionButton(navController: NavController) {
    Button(
        onClick = { navController.navigate(Routes.Home.ruta) },
        enabled = true,
        border = BorderStroke(1.dp, Color.Red),
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.Red)
    ) {
        Text(text = "Cerrar sesion")
    }
}