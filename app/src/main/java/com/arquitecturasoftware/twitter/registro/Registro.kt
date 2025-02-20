@file:Suppress("DEPRECATION")

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
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.arquitecturasoftware.twitter.R
import com.arquitecturasoftware.twitter.login.LoginViewModel
import com.arquitecturasoftware.twitter.login.ui.Password
import com.arquitecturasoftware.twitter.routes.Routes
import androidx.compose.material3.Text as Text

@Composable
fun RegistroScreen(loginViewModel: LoginViewModel, navigationController: NavHostController) {
    val nombre : String by loginViewModel.nombre.observeAsState("")
    val email : String by loginViewModel.email.observeAsState("")
    val fechaNacimiento : String by loginViewModel.fechaNacimiento.observeAsState("")
    val isEnable : Boolean by loginViewModel.isRegisterEnable.observeAsState(false)
    Column (Modifier.fillMaxSize().padding(top = 26.dp, start = 16.dp, end = 16.dp).imePadding()){
        Header(navigationController)
        Spacer(modifier = Modifier.size(2.dp))
        RegistroText()
        Spacer(modifier = Modifier.size(16.dp))
        Nombre(nombre) { loginViewModel.onRegistroChanges(it, email, fechaNacimiento) }
        Spacer(modifier = Modifier.size(16.dp))
        Email(email) { loginViewModel.onRegistroChanges(nombre, it, fechaNacimiento) }
        Spacer(modifier = Modifier.size(16.dp))
        FechaNacimiento(fechaNacimiento) { loginViewModel.onRegistroChanges(nombre, email, it) }
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
fun CodigoVerificacion(loginViewModel: LoginViewModel, navigationController: NavController) {
    val email: String by loginViewModel.email.observeAsState("")
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
            Header2(navigationController)
            Spacer(modifier = Modifier.size(2.dp))
            CodigoTextTittle()
            CodigoText(email)
            CodigoVerificacion(codigo) { loginViewModel.onCodigoChanges(it) }
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
fun ContraRegistro(loginViewModel: LoginViewModel, navigationController: NavController) {
    val password: String by loginViewModel.password.observeAsState("")
    val isEnable: Boolean by loginViewModel.isLoginEnablePassword.observeAsState(false)

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
        Password(password) { loginViewModel.onLoginChangesPassword(it) }
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
    Button(onClick = { navController.navigate(Routes.CodigoVerificacion.ruta) }, enabled = loginEnable, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black, disabledContainerColor = Color.LightGray, disabledContentColor = Color.Gray)) {
        Text(text = "Siguiente")
    }
}

@Composable
fun CodigoTextTittle(){
    Text(text = "Te enviamos un código", fontSize = 36.sp, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp), lineHeight = 40.sp)
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
    Button(onClick = {  }, enabled = loginEnable, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black, disabledContainerColor = Color.LightGray, disabledContentColor = Color.Gray)) {
        Text(text = "Registrarte")
    }
}