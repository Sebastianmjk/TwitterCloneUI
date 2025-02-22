package com.arquitecturasoftware.twitter.login.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arquitecturasoftware.twitter.api.response.TokenResponse
import com.arquitecturasoftware.twitter.api.response.UsersProfileResponse
import com.arquitecturasoftware.twitter.login.LoginViewModel
import com.arquitecturasoftware.twitter.routes.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen2(loginViewModel: LoginViewModel, navController: NavController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .imePadding()) {
        val isLoading: Boolean by loginViewModel.isLoading.observeAsState(false)
        val email: String by loginViewModel.email.observeAsState("")
        val isLoginEnablePassword: Boolean by loginViewModel.isLoginEnablePassword.observeAsState(
            false
        )
        val password: String by loginViewModel.password.observeAsState("")
        val loginResult: TokenResponse? by loginViewModel.loginResult.observeAsState(null)
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(loginResult) {
            loginResult?.let {
                navController.navigate(Routes.Inicio.ruta)
            }
        }
        if (isLoading) {
            Box(modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 26.dp)) {
                Header(navController)
                TextoPassword()
                TextFieldEmail(email)
                Spacer(modifier = Modifier.height(16.dp))
                Password(password) { loginViewModel.onLoginChangesPassword(it) }
                Spacer(modifier = Modifier.weight(1f))
                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 48.dp)
                ) {
                    Row(Modifier.padding(start = 16.dp, end = 16.dp)) {
                        OlvidarContrasena(navController)
                        Spacer(modifier = Modifier.width(10.dp))
                        IniciarButton(isLoginEnablePassword) {
                            coroutineScope.launch {
                                val loginSuccessful = loginViewModel.login(email, password)
                                if (!loginSuccessful) {
                                    Toast.makeText(
                                        navController.context,
                                        "Usuario o contraseña incorrectos",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TextoPassword() {
    Text(
        "Introduce tu contraseña",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun TextFieldEmail(email: String) {
    TextField(
        value = email,
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(BorderStroke(1.dp, Color.Gray)),
        enabled = false,
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.White,
            disabledContainerColor = Color.Black,
            disabledLabelColor = Color.Gray,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun Password(password: String, onTextChanged: (String) -> Unit) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = { Text(text = "Contraseña") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            focusedContainerColor = Color.Black,
            unfocusedContainerColor = Color.Black,
            unfocusedTextColor = Color.Gray,
            focusedIndicatorColor = Color(0xFFADD8E6), // Border color when focused
            cursorColor = Color(0xFFADD8E6),
            focusedLabelColor = Color(0xFFADD8E6)
        ),
        trailingIcon = {
            val image = if (passwordVisibility) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = image, contentDescription = "show password")
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}

@Composable
fun IniciarButton(loginEnable: Boolean, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        enabled = loginEnable,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.Gray
        )
    ) {
        Text(text = "Iniciar sesión")
    }
}