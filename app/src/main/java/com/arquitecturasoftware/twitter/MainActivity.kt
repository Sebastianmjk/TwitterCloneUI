package com.arquitecturasoftware.twitter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arquitecturasoftware.twitter.home.HomeScreen
import com.arquitecturasoftware.twitter.inicio.ui.AddTweet
import com.arquitecturasoftware.twitter.inicio.ui.InicioScreen
import com.arquitecturasoftware.twitter.login.LoginScreen
import com.arquitecturasoftware.twitter.login.LoginScreen2
import com.arquitecturasoftware.twitter.login.LoginViewModel
import com.arquitecturasoftware.twitter.registro.CodigoVerificacion
import com.arquitecturasoftware.twitter.registro.ContraRegistro
import com.arquitecturasoftware.twitter.registro.RegistroScreen
import com.arquitecturasoftware.twitter.routes.Routes
import com.arquitecturasoftware.twitter.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize() , color = Color.Black
                ) {
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.Home.ruta
                    ) {
                        composable(Routes.Inicio.ruta) { InicioScreen(navigationController) }
                        composable(Routes.AddTweet.ruta) { AddTweet(navigationController) }
                        composable(Routes.LoginEmail.ruta) { LoginScreen(loginViewModel, navigationController) }
                        composable(Routes.LoginPassword.ruta) { LoginScreen2(loginViewModel, navigationController) }
                        composable(Routes.Home.ruta) { HomeScreen(navigationController) }
                        composable(Routes.Registro.ruta) { RegistroScreen(loginViewModel, navigationController) }
                        composable(Routes.CodigoVerificacion.ruta) { CodigoVerificacion(loginViewModel, navigationController) }
                        composable(Routes.RegisterPassword.ruta) { ContraRegistro(loginViewModel, navigationController) }
                    }
                }
            }
        }
    }
}