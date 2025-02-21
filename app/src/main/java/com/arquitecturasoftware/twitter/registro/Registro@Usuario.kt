package com.arquitecturasoftware.twitter.registro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arquitecturasoftware.twitter.routes.Routes

@Composable
fun RegistroArrobaNombre(registroViewModel: RegistroViewModel, navigationController: NavController) {
    val nombreUsuario: String by registroViewModel.arrobaNombre.observeAsState("")
    val isEnable: Boolean by registroViewModel.isArrobaNameEnbable.observeAsState(false)

    Box(modifier = Modifier
        .fillMaxSize()
        .imePadding()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 26.dp, start = 16.dp, end = 16.dp)
        ) {
            com.arquitecturasoftware.twitter.home.Header()
            Spacer(modifier = Modifier.size(2.dp))
            NombreUsuarioTextTittle()
            NombreUsuarioText()
            NombreArroba (nombreUsuario) { registroViewModel.onRegistroChangesArrobaNombre(it) }
            Spacer(modifier = Modifier.weight(1f))
            HorizontalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))
            Box(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 48.dp)
                .imePadding()) {
                Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                    Spacer(modifier = Modifier.weight(1f))
                    SiguienteButtonNombreArroba(isEnable, navigationController)
                }
            }
        }
    }
}

@Composable
fun NombreArroba(nombreUsuario: String, onTextChanged:(String) -> Unit) {
    TextField(
        value = nombreUsuario,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        label = { Text(text = "Nombre de usuario") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            focusedContainerColor = Color.Black,
            unfocusedContainerColor = Color.Black,
            unfocusedTextColor = Color.Gray,
            focusedIndicatorColor = Color(0xFFADD8E6),
            cursorColor = Color(0xFFADD8E6),
            focusedLabelColor = Color(0xFFADD8E6)
        )
    )
}

@Composable
fun NombreUsuarioTextTittle(){
    Text(text = "¿Como te llamas?", fontSize = 36.sp, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp), lineHeight = 40.sp)
}

@Composable
fun NombreUsuarioText(){
    Text(text = "Tu @usuario es único. Puedes cambiarlo cuando quiero.", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(16.dp))
}

@Composable
fun SiguienteButtonNombreArroba(loginEnable: Boolean, navController: NavController) {
    Button(onClick = { navController.navigate(Routes.Home.ruta) }, enabled = loginEnable, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black, disabledContainerColor = Color.LightGray, disabledContentColor = Color.Gray)) {
        Text(text = "Siguiente")
    }
}