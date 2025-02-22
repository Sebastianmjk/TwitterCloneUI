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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.arquitecturasoftware.twitter.R
import com.arquitecturasoftware.twitter.routes.Routes
import androidx.compose.material3.Text as Text

@Composable
fun RegistroScreen(registroViewModel: RegistroViewModel, navigationController: NavHostController) {
    val nombre : String by registroViewModel.nombre.observeAsState("")
    val email : String by registroViewModel.email.observeAsState("")
    val isEnable : Boolean by registroViewModel.isRegisterEnable.observeAsState(false)
    Column (Modifier.fillMaxSize().padding(top = 26.dp, start = 16.dp, end = 16.dp).imePadding()){
        Header(navigationController)
        Spacer(modifier = Modifier.size(2.dp))
        RegistroText()
        Spacer(modifier = Modifier.size(16.dp))
        Nombre(nombre) { registroViewModel.onRegistroChanges(it, email) }
        Spacer(modifier = Modifier.size(16.dp))
        Email(email) { registroViewModel.onRegistroChanges(nombre, it) }
        Spacer(modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.weight(1f))
        HorizontalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))
        Box(modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 48.dp)) {
            Row (Modifier.padding(start = 16.dp, end = 16.dp)){
                Spacer(modifier = Modifier.weight(1f))
                SiguienteButtonRegister(isEnable, navigationController)
            }
        }
    }
}

@Composable
fun Header(navController: NavController){
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 40.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Comeback",
            modifier = Modifier
                .clickable { navController.navigate(Routes.Home.ruta) },
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
fun RegistroText(){
    Text(text = "Crea tu cuenta", fontSize = 36.sp, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp))
}

@Composable
fun Nombre(nombre: String, onTextChanged:(String) -> Unit) {
    TextField(
        value = nombre,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        label = { Text(text = "Nombre") },
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
fun Email(email: String, onTextChanged:(String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        label = { Text(text = "Email") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
fun FechaNacimiento(fechaNacimiento: String, onTextChanged:(String) -> Unit) {
    TextField(
        value = fechaNacimiento,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        label = { Text(text = "Fecha de Nacimiento") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
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
fun SiguienteButtonRegister(loginEnable: Boolean, navController: NavController) {
    Button(onClick = { navController.navigate(Routes.RegisterPassword.ruta) }, enabled = loginEnable, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black, disabledContainerColor = Color.LightGray, disabledContentColor = Color.Gray)) {
        Text(text = "Siguiente")
    }
}

@Composable
fun CodigoTextTittle(){
    Text(text = "Te enviamos un código", fontSize = 36.sp, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp), lineHeight = 40.sp)
}