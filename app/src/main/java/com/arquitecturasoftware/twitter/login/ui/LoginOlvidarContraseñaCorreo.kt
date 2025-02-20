package com.arquitecturasoftware.twitter.login.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arquitecturasoftware.twitter.login.LoginViewModel
import com.arquitecturasoftware.twitter.routes.Routes

@Composable
fun LoginOlvidarContrasena(loginViewModel: LoginViewModel, navController: NavController) {
    Box(modifier = Modifier.fillMaxSize().imePadding()) {
        val isLoading: Boolean by loginViewModel.isLoading.observeAsState(false)
        val email: String by loginViewModel.email.observeAsState("")
        val isEnable: Boolean by loginViewModel.isLoginEnable.observeAsState(false)
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().align(Alignment.Center)) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(top = 26.dp)) {
                Header(navController)
                TextoOlvidarContraCorreo()
                DescripcionOlvidarContraCorreo()
                Email(email) { loginViewModel.onLoginChangesEmail(email = it) }
                Spacer(modifier = Modifier.weight(1f))
                HorizontalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))
                Box(modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 48.dp)) {
                    Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                        Spacer(modifier = Modifier.weight(1f))
                        SiguienteButtonOlvidarContra(isEnable, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun TextoOlvidarContraCorreo(){
    Text("Encuentra tu cuenta de Twitter", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(16.dp), lineHeight = 40.sp)
}

@Composable
fun DescripcionOlvidarContraCorreo(){
    Text(text = "Introdúcelo el correo electrónico asociado a tu cuenta para cambiar tu contraseña.", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(16.dp))
}

@Composable
fun SiguienteButtonOlvidarContra(loginEnable: Boolean, navController: NavController) {
    Button(onClick = { navController.navigate(Routes.OlvidarContraCodigo.ruta) }, enabled = loginEnable, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black, disabledContainerColor = Color.LightGray, disabledContentColor = Color.Gray)) {
        Text(text = "Siguiente")
    }
}