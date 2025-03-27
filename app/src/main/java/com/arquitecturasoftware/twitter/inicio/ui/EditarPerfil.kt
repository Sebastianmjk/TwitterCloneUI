package com.arquitecturasoftware.twitter.inicio.ui

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.arquitecturasoftware.twitter.R
import com.arquitecturasoftware.twitter.api.TokenManager
import com.arquitecturasoftware.twitter.login.LoginViewModel
import com.arquitecturasoftware.twitter.routes.Routes
import kotlinx.coroutines.launch

@Composable
fun EditarPerfil(loginViewModel: LoginViewModel, navController: NavController, profileViewModel: ProfileViewModel) {
    val nombre: String by loginViewModel.nombre.observeAsState("")
    val password: String by loginViewModel.password.observeAsState("")
    val token = "Bearer "+TokenManager.accessToken
    Column(modifier = Modifier.fillMaxWidth().imePadding()) {
        HeaderEditarPerfil(navController)
        BodyEditarPerfil(loginViewModel)
        Spacer(Modifier.weight(1f))
        HorizontalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))
        Box(modifier = Modifier.align(Alignment.CenterHorizontally).padding(start = 16.dp, end = 16.dp, bottom = 48.dp)) {
            GuardarButton(){
                profileViewModel.viewModelScope.launch {
                    val success = profileViewModel.updateProfile(token,nombre, password)
                    if (success) {
                        navController.navigate(Routes.Perfil.ruta)
                    } else {
                        Log.e("EditarPerfil", "Error al actualizar el perfil")
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderEditarPerfil(navController: NavController) {
    Box(Modifier.fillMaxWidth().padding(top = 26.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.navigate(Routes.Perfil.ruta) }, colors = IconButtonDefaults.iconButtonColors(
                contentColor = Color.White
            )) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
            }
            Spacer(Modifier.weight(1f))
            EditarPerfilTittle()
            Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
fun EditarPerfilTittle() {
    Text(text = "Editar Perfil", fontSize = 24.sp, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp))
}

@Composable
fun GuardarButton(onClick:() -> Unit) {
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.Gray
        )
    ) {
        Text(text = "Guardar", color = Color.Black)
    }
}

@Composable
fun BodyEditarPerfil(loginViewModel: LoginViewModel) {
    val nombre: String by loginViewModel.nombre.observeAsState("")
    val password: String by loginViewModel.password.observeAsState("")

    Column(Modifier.padding(16.dp)) {
        Spacer(Modifier.size(16.dp))
        NombreEditarPerfil(nombre) { loginViewModel.setNombre(it) }
        ContraEditarPerfil(password) { loginViewModel.setPassword(it) }
    }
}

@Composable
fun NombreEditarPerfil(nombre: String, onTextChanged: (String) -> Unit) {
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
fun ContraEditarPerfil(password: String, onTextChanged: (String) -> Unit) {
    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        label = { Text(text = "Contraseña") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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