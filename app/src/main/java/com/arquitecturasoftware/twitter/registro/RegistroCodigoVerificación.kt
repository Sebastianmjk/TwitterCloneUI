package com.arquitecturasoftware.twitter.registro

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.arquitecturasoftware.twitter.R
import com.arquitecturasoftware.twitter.routes.Routes

@Composable
fun CodigoVerificacion(registroViewModel: RegistroViewModel, navigationController: NavController) {
    val email: String by registroViewModel.email.observeAsState("")
    val codigo: String by registroViewModel.codigo.observeAsState("")
    val isEnable: Boolean by registroViewModel.isCodigoEnable.observeAsState(false)

    Box(modifier = Modifier
        .fillMaxSize()
        .imePadding()
        .zIndex(1f)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 26.dp, start = 16.dp, end = 16.dp)
        ) {
            Header2(navigationController)
            Spacer(modifier = Modifier.size(2.dp))
            CodigoTextTittle()
            CodigoText(email)
            CodigoVerificacion(codigo) { registroViewModel.onCodigoChanges(it) }
            NoRecibioText(navigationController)
            Spacer(modifier = Modifier.weight(1f))
            HorizontalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))
            Box(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 48.dp)
                .imePadding()) {
                Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                    Spacer(modifier = Modifier.weight(1f))
                    SiguienteButtonCodigo(isEnable, navigationController)
                }
            }
        }
    }
}

@Composable
fun Header2(navController: NavController){
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 40.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Comeback",
            modifier = Modifier
                .clickable { navController.navigate(Routes.Registro.ruta) },
            tint = Color.White
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painterResource(id = R.drawable.logo_twitter),
            contentDescription = "Logo",
            modifier = Modifier.align(Alignment.CenterVertically).clip(shape = CircleShape).size(55.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun CodigoText(email: String){
    Text(text = "Introdúcelo abajo para verificar $email", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(16.dp))
}

@Composable
fun CodigoVerificacion(codigo: String, onTextChanged:(String) -> Unit) {
    TextField(
        value = codigo,
        onValueChange = { onTextChanged(it)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        label = { Text(text = "Código de verificación") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            focusedContainerColor = Color.Black,
            unfocusedContainerColor = Color.Black,
            unfocusedTextColor = Color.Gray,
            focusedIndicatorColor = Color(0xFFADD8E6), // Border color when focused
            cursorColor = Color(0xFFADD8E6),
            focusedLabelColor = Color(0xFFADD8E6)
        )
    )
}

@Composable
fun NoRecibioText(navigationController: NavController) {
    Text(
        "¿No recibiste el correo electrónico?",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF4EA8E9),
        modifier = Modifier.clickable {  }.padding(16.dp))
}

@Composable
fun SiguienteButtonCodigo(loginEnable: Boolean, navController: NavController) {
    Button(onClick = { navController.navigate(Routes.RegisterPassword.ruta) }, enabled = loginEnable, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black, disabledContainerColor = Color.LightGray, disabledContentColor = Color.Gray)) {
        Text(text = "Siguiente")
    }
}
