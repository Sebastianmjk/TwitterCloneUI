package com.arquitecturasoftware.twitter.inicio.ui

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arquitecturasoftware.twitter.R
import com.arquitecturasoftware.twitter.routes.Routes

@Composable
fun InicioScreen(navController: NavController) {
    DisableBackPressHandler()
    Scaffold( containerColor = Color.Black,
        topBar = {
            HeaderInicio(navController)
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
            LazyColumn {
                items(3) {
                    TweetDesign()
                }
            }
        }
    }
}

@Composable
fun HeaderInicio(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "profile picture",
            modifier = Modifier.clip(shape = CircleShape).size(55.dp)
        )
        Spacer(modifier = Modifier.weight(0.9f))
        Image(
            painter = painterResource(id = R.drawable.logo_twitter),
            contentDescription = "profile picture",
            modifier = Modifier.clip(shape = CircleShape).size(55.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = {navController.navigate(Routes.Home.ruta) }, colors = IconButtonDefaults.iconButtonColors(
            contentColor = Color.White
        )) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar sesion")
        }
    }
}

@Composable
fun MyBottomNavigationInicio(navController: NavController) {
    var indexMio by rememberSaveable { mutableIntStateOf(0) }
    NavigationBar(containerColor = Color.Black) {
        NavigationBarItem(
            selected = indexMio == 0,
            onClick = {
                indexMio = 0
                navController.navigate(Routes.Inicio.ruta) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "home"
                )
            },
            label = {
                Text(text = "Inicio", color = Color.White)
            },
            colors = NavigationBarItemDefaults.colors(Color.White)
        )
        NavigationBarItem(
            selected = indexMio == 1,
            onClick = {
                indexMio = 1
                navController.navigate(Routes.Perfil.ruta) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "person"
                )
            },
            label = {
                Text(text = "Perfil", color = Color.White)
            },
            colors = NavigationBarItemDefaults.colors(Color.White)
        )
    }
}

@Composable
fun Fab(onAbrirMenu: ()->Unit) {
    FloatingActionButton(onClick = { onAbrirMenu() },
        containerColor = Color.Blue,
        contentColor = Color.White

    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
    }
}

@Composable
fun TweetsList(addTweetViewModel: AddTweetViewModel){
    LazyColumn {
//        TweetDesign()
    }
}

@Composable
fun DisableBackPressHandler() {
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
    }

    DisposableEffect(backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}