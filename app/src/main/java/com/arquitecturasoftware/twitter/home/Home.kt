package com.arquitecturasoftware.twitter.home

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arquitecturasoftware.twitter.R
import com.arquitecturasoftware.twitter.routes.Routes
import androidx.activity.compose.BackHandler

@Composable
fun HomeScreen(navigationController: NavHostController) {
    BackHandler {
        (navigationController.context as? ComponentActivity)?.finish()
    }
    Column (Modifier.fillMaxSize().padding(top = 26.dp, start = 16.dp, end = 16.dp)){
        Header()
        Spacer(modifier = Modifier.size(100.dp))
        TextHome()
        Spacer(modifier = Modifier.size(150.dp))
        SocialHome()
        HomeDivider()
        CrearCuentaButton(navigationController)
        PoliticsText()
        Spacer(modifier = Modifier.size(16.dp))
        IniciarSesionText(navigationController)
    }
}

@Composable
fun Header(){
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.logo_twitter),
            contentDescription = "Twitter",
            alignment = Alignment.Center,
            modifier = Modifier.clip(shape = CircleShape).size(55.dp)

        )
    }
}

@Composable
fun HomeDivider() {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        HorizontalDivider(Modifier.background(Color(0xFFF9F9F9)).height(1.dp).weight(1f))
        Text(text = "O", modifier = Modifier.padding(horizontal = 18.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB5B5B5))
        HorizontalDivider(Modifier.background(Color(0xFFF9F9F9)).height(1.dp).weight(1f))
    }
}

@Composable
fun SocialHome(){
    Box(Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)) {
        Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                Image(painter = painterResource(id = R.drawable.logo_google), contentDescription = "Social login google", modifier = Modifier.size(16.dp).clip(CircleShape))
                Text(text = "Continuar con Google", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
    }
}

@Composable
fun CrearCuentaButton(navigationController: NavHostController) {
    Box(Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)) {
        Button(onClick = { navigationController.navigate(Routes.Registro.ruta) }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)) {
            Text(text = "Crear Cuenta", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun TextHome(){
    Text(text = "Entérate de lo que está pasando en el mundo en este momento.", fontSize = 36.sp, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp),lineHeight = 40.sp)
}


@Composable
fun PoliticsText() {
    val uriHandler = LocalUriHandler.current
    val annotatedString = remember {
        buildAnnotatedString {
            append("Al registrarte, aceptas los ")
            pushStringAnnotation(tag = "URL", annotation = "https://twitter.com/tos")
            withStyle(style = SpanStyle(color = Color(0xFF4EA8E9), fontWeight = FontWeight.Bold)) {
                append("Términos")
            }
            pop()
            append(", la ")
            pushStringAnnotation(tag = "URL", annotation = "https://twitter.com/privacy")
            withStyle(style = SpanStyle(color = Color(0xFF4EA8E9), fontWeight = FontWeight.Bold)) {
                append("Política de privacidad")
            }
            pop()
            append(" y la ")
            pushStringAnnotation(tag = "URL", annotation = "https://help.x.com/es/rules-and-policies/x-cookies")
            withStyle(style = SpanStyle(color = Color(0xFF4EA8E9), fontWeight = FontWeight.Bold)) {
                append("Política de cookies")
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
        modifier = Modifier.fillMaxWidth().padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    )
}

@Composable
fun IniciarSesionText(navigationController: NavHostController) {
    Row (Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp), horizontalArrangement = Arrangement.Center){
        Text(text = "¿Ya tienes una cuenta?", fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            "Iniciar sesión",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4EA8E9),
            modifier = Modifier.clickable { navigationController.navigate(Routes.LoginEmail.ruta) })
    }
}