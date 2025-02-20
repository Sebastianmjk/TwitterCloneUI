package com.arquitecturasoftware.twitter.login.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.arquitecturasoftware.twitter.login.LoginViewModel
import com.arquitecturasoftware.twitter.registro.CodigoTextTittle
import com.arquitecturasoftware.twitter.registro.CodigoVerificacion
import com.arquitecturasoftware.twitter.routes.Routes

@Composable
fun LoginCodigoVerificacion(loginViewModel: LoginViewModel, navigationController: NavController) {
    val codigo: String by loginViewModel.codigo.observeAsState("")
    val isEnable: Boolean by loginViewModel.isCodigoEnable.observeAsState(false)
    Box(modifier = Modifier
        .fillMaxSize()
        .imePadding()
        .zIndex(1f)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 26.dp, start = 16.dp, end = 16.dp)
        ) {
            Header(navigationController)
            Spacer(modifier = Modifier.size(2.dp))
            CodigoTextTittle()
            DescripcionCodigoOlvidarContra()
            CodigoVerificacion(codigo) { loginViewModel.onCodigoChanges(it) }
            Spacer(modifier = Modifier.weight(1f))
            HorizontalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))
            Box(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 48.dp)
                .imePadding()) {
                Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                    Spacer(modifier = Modifier.weight(1f))
                    SiguienteButtonCodigoOlvidarContra(isEnable, navigationController)
                }
            }
        }
    }
}

@Composable
fun DescripcionCodigoOlvidarContra(){
    Text(text = "Consulta tu correo electrónico para obtener tu código de confirmación. Si tienes que solicitar un código nuevo, vuelve y selecciona un método de confirmación de nuevo.", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp))
}

@Composable
fun SiguienteButtonCodigoOlvidarContra(loginEnable: Boolean, navController: NavController) {
    Button(onClick = { navController.navigate(Routes.LoginNewContrasena.ruta) }, enabled = loginEnable, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black, disabledContainerColor = Color.LightGray, disabledContentColor = Color.Gray)) {
        Text(text = "Siguiente")
    }
}