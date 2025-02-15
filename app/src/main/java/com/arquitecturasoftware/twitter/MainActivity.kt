package com.arquitecturasoftware.twitter

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
import com.arquitecturasoftware.twitter.inicio.ui.InicioViewModel
import com.arquitecturasoftware.twitter.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arquitecturasoftware.twitter.routes.Routes

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val inicioViewModel : InicioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface (Modifier.fillMaxSize()) {
                    val navigationController = rememberNavController()
                    NavHost(navController = navigationController, startDestination = Routes.Inicio.ruta) {
                        composable(Routes.Inicio.ruta) { InicioScreen(navigationController) }
                        composable(Routes.AddTweet.ruta) { AddTweet(navigationController) }
                    }
                }
            }
        }
    }
}
