package com.arquitecturasoftware.twitter

import com.arquitecturasoftware.twitter.login.LoginScreen
import com.arquitecturasoftware.twitter.login.LoginScreen2
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.arquitecturasoftware.twitter.inicio.ui.AddTweet
import com.arquitecturasoftware.twitter.inicio.ui.InicioScreen
import com.arquitecturasoftware.twitter.inicio.ui.AddTweetViewModel
import com.arquitecturasoftware.twitter.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arquitecturasoftware.twitter.home.HomeScreen
import com.arquitecturasoftware.twitter.login.LoginViewModel
import com.arquitecturasoftware.twitter.registro.RegistroScreen
import com.arquitecturasoftware.twitter.routes.Routes

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val addTweetViewModel : AddTweetViewModel by viewModels()
    private val loginViewModel : LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(Modifier.fillMaxSize()) {
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.Home.ruta
                    ) {
                        composable(Routes.Inicio.ruta) { InicioScreen(navigationController) }
                        composable(Routes.AddTweet.ruta) { AddTweet(navigationController) }
                        composable(Routes.LoginEmail.ruta) { LoginScreen(loginViewModel,navigationController) }
                        composable(Routes.LoginPassword.ruta) { LoginScreen2(loginViewModel,navigationController) }
                        composable(Routes.Home.ruta) { HomeScreen(navigationController) }
                        composable(Routes.Registro.ruta) { RegistroScreen(loginViewModel,navigationController) }
                    }
                }
            }
        }
    }
}

