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
import com.arquitecturasoftware.twitter.api.TokenManager
import com.arquitecturasoftware.twitter.home.HomeScreen
import com.arquitecturasoftware.twitter.inicio.ui.AddTweet
import com.arquitecturasoftware.twitter.inicio.ui.AddTweetViewModel
import com.arquitecturasoftware.twitter.inicio.ui.ComentScreen
import com.arquitecturasoftware.twitter.inicio.ui.EditarPerfil
import com.arquitecturasoftware.twitter.inicio.ui.InicioScreen
import com.arquitecturasoftware.twitter.inicio.ui.ProfileScreen
import com.arquitecturasoftware.twitter.inicio.ui.ProfileViewModel
import com.arquitecturasoftware.twitter.inicio.ui.TweetsViewModel
import com.arquitecturasoftware.twitter.login.ui.LoginScreen
import com.arquitecturasoftware.twitter.login.ui.LoginScreen2
import com.arquitecturasoftware.twitter.login.LoginViewModel
import com.arquitecturasoftware.twitter.login.SharedViewModel
import com.arquitecturasoftware.twitter.login.ui.LoginCodigoVerificacion
import com.arquitecturasoftware.twitter.login.ui.LoginNewContrasena
import com.arquitecturasoftware.twitter.login.ui.LoginOlvidarContrasena
import com.arquitecturasoftware.twitter.registro.CodigoVerificacion
import com.arquitecturasoftware.twitter.registro.ContraRegistro
import com.arquitecturasoftware.twitter.registro.RegistroArrobaNombre
import com.arquitecturasoftware.twitter.registro.RegistroScreen
import com.arquitecturasoftware.twitter.registro.RegistroViewModel
import com.arquitecturasoftware.twitter.routes.Routes
import com.arquitecturasoftware.twitter.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val registroViewModel: RegistroViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val addTweetViewModel: AddTweetViewModel by viewModels()
    private val tweetsViewModel: TweetsViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

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
                        startDestination = if (TokenManager.accessToken?.isEmpty() == true) Routes.LoginEmail.ruta else Routes.Home.ruta
                    ) {
                        composable(Routes.Inicio.ruta) { InicioScreen(navigationController, sharedViewModel, tweetsViewModel,profileViewModel) }
                        composable(Routes.AddTweet.ruta) { AddTweet(navigationController, addTweetViewModel) }
                        composable(Routes.LoginEmail.ruta) { LoginScreen(loginViewModel, navigationController) }
                        composable(Routes.LoginPassword.ruta) { LoginScreen2(loginViewModel, navigationController) }
                        composable(Routes.Home.ruta) { HomeScreen(navigationController) }
                        composable(Routes.Registro.ruta) { RegistroScreen(registroViewModel, navigationController) }
                        composable(Routes.CodigoVerificacion.ruta) { CodigoVerificacion(registroViewModel, navigationController) }
                        composable(Routes.RegisterPassword.ruta) { ContraRegistro(registroViewModel, navigationController) }
                        composable(Routes.Perfil.ruta) { ProfileScreen(
                            navigationController, loginViewModel, sharedViewModel,
                            profileViewModel = profileViewModel,
                            tweetsViewModel = tweetsViewModel
                        ) }
                        composable(Routes.OlvidarContrasena.ruta) { LoginOlvidarContrasena(loginViewModel, navigationController) }
                        composable(Routes.OlvidarContraCodigo.ruta) { LoginCodigoVerificacion(loginViewModel, navigationController) }
                        composable(Routes.LoginNewContrasena.ruta) { LoginNewContrasena(loginViewModel, navigationController) }
                        composable(Routes.RegistroArrobaNombre.ruta) { RegistroArrobaNombre(registroViewModel, navigationController) }
                        composable(Routes.EditarPerfil.ruta) { EditarPerfil(loginViewModel, navigationController, profileViewModel) }
                        composable("${Routes.Comentarios.ruta}/{tweetId}") { backStackEntry ->
                            val tweetId = backStackEntry.arguments?.getString("tweetId")?.toInt() ?: 0
                            ComentScreen(navigationController, tweetsViewModel, tweetId)
                        }
                    }
                }
            }
        }
    }
}