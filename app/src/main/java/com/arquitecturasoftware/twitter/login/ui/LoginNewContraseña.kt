@file:Suppress("DEPRECATION")

package com.arquitecturasoftware.twitter.login.ui

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arquitecturasoftware.twitter.login.LoginViewModel
import com.arquitecturasoftware.twitter.routes.Routes

@Composable
fun LoginNewContrasena(loginViewModel: LoginViewModel, navController: NavController) {
    Box(modifier = Modifier.fillMaxSize().imePadding()) {
        val isLoading: Boolean by loginViewModel.isLoading.observeAsState(false)
        val email: String by loginViewModel.email.observeAsState("")
        val isEnable: Boolean by loginViewModel.isLoginNewEnablePassword.observeAsState(false)
        val password: String by loginViewModel.password.observeAsState("")
        val newPassword : String by loginViewModel.newPassword.observeAsState("")
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().align(Alignment.Center)) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(top = 26.dp)) {
                Header(navController)
                TextoNewPassword()
                DescripcionNewContrasena()
                TextFieldEmail(email)
                Password(password) { loginViewModel.onLoginChangesPassword(it) }
                NewPassword(newPassword) { loginViewModel.onLoginChangesNewPassword(password, it) }
                Spacer(modifier = Modifier.weight(1f))
                HorizontalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))
                Box(modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 48.dp)) {
                    Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                        Spacer(modifier = Modifier.weight(1f))
                        CambiarContraButton(isEnable, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun TextoNewPassword(){
    Text("Elige una contraseña nueva", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(start = 16.dp , bottom = 16.dp, end = 16.dp), lineHeight = 40.sp)
}
@Composable
fun DescripcionNewContrasena() {
    val uriHandler = LocalUriHandler.current
    val annotatedString = remember {
        buildAnnotatedString {
            append("Asegúrate de que tu contraseña tenga 8 caracteres o más. Intenta que incluya números, letras y signos de puntuación para que sea una . ")
            pushStringAnnotation(tag = "URL", annotation = "https://help.x.com/en/safety-and-security/account-security-tips?ref=password_reset")
            withStyle(style = SpanStyle(color = Color(0xFF4EA8E9), fontWeight = FontWeight.Bold)) {
                append("contraseña segura")
            }
            pop()
            append(".")
        }
    }

    ClickableText(
        text = annotatedString,
        style = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = Color.Gray, lineHeight = 16.sp),
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item)
                }
        },
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)
    )
}

@Composable
fun NewPassword(password: String, onTextChanged: (String) -> Unit) {
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
fun CambiarContraButton(loginEnable: Boolean, navController: NavController) {
    Button(
        onClick = {
            navController.navigate(Routes.Home.ruta) },
        enabled = loginEnable,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.Gray
        )
    ) {
        Text(text = "Cambiar contraseña")
    }

}