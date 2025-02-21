@file:Suppress("DEPRECATION")

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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.arquitecturasoftware.twitter.login.ui.Password
import com.arquitecturasoftware.twitter.routes.Routes

@Composable
fun ContraRegistro(registroViewModel: RegistroViewModel, navigationController: NavController) {
    val password: String by registroViewModel.password.observeAsState("")
    val isEnable: Boolean by registroViewModel.isRegistroEnablePassword.observeAsState(false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 26.dp, start = 16.dp, end = 16.dp)
            .imePadding()
            .zIndex(1f)
    ) {
        com.arquitecturasoftware.twitter.home.Header()
        Spacer(modifier = Modifier.size(10.dp))
        ContrasenaTextTittle()
        ContrasenaText()
        Password(password) { registroViewModel.onRegistroChangesPassword(it) }
        PoliticsTextContra()
        Spacer(modifier = Modifier.weight(1f))
        HorizontalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 48.dp)
                .imePadding()
        ) {
            Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                SiguienteButtonContrasena(isEnable, navigationController)
            }
        }
    }
}

@Composable
fun ContrasenaTextTittle(){
    Text(text = "Necesitarás una contraseña", fontSize = 36.sp, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp), lineHeight = 40.sp)
}

@Composable
fun ContrasenaText(){
    Text(text = "Asegurate de que tenga 8 caracteres o más.", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(16.dp))
}

@Composable
fun PoliticsTextContra() {
    val uriHandler = LocalUriHandler.current
    val annotatedString = remember {
        buildAnnotatedString {
            append("Al registrarte, aceptas los ")
            pushStringAnnotation(tag = "URL", annotation = "https://twitter.com/tos")
            withStyle(style = SpanStyle(color = Color(0xFF4EA8E9), fontWeight = FontWeight.Bold)) {
                append("Términos de servicio")
            }
            pop()
            append("y la ")
            pushStringAnnotation(tag = "URL", annotation = "https://twitter.com/privacy")
            withStyle(style = SpanStyle(color = Color(0xFF4EA8E9), fontWeight = FontWeight.Bold)) {
                append("Política de privacidad")
            }
            pop()
            append(", incluida la politica de ")
            pushStringAnnotation(tag = "URL", annotation = "https://help.x.com/es/rules-and-policies/x-cookies")
            withStyle(style = SpanStyle(color = Color(0xFF4EA8E9), fontWeight = FontWeight.Bold)) {
                append("Uso de cookies")
            }
            pop()
            append(". X puede usar tu información de contacto, como tu dirección de correo electrónico y tu número de télefono, con los " +
                    "fines descritos en nueestra Política de privacidad, por ejemplo para mantener tu cuenta segura y personalizar nuestros servicios, incluidos" +
                    " los anuncios.")
            pushStringAnnotation(tag = "URL", annotation = "https://twitter.com/privacy")
            withStyle(style = SpanStyle(color = Color(0xFF4EA8E9), fontWeight = FontWeight.Bold)) {
                append("Más información")
            }
            pop()
            append(". Otras personas podrán encontrarte por tu correo electrónico o número de teléfono, si los proporcionaste, a menos que elijas otra opción.")
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
        modifier = Modifier.fillMaxWidth().padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    )
}

@Composable
fun SiguienteButtonContrasena(loginEnable: Boolean, navController: NavController) {
    Button(onClick = { navController.navigate(Routes.RegistroArrobaNombre.ruta) }, enabled = loginEnable, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black, disabledContainerColor = Color.LightGray, disabledContentColor = Color.Gray)) {
        Text(text = "Registrarte")
    }
}