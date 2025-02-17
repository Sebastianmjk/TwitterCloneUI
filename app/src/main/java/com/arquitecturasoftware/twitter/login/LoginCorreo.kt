import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arquitecturasoftware.twitter.R
import com.arquitecturasoftware.twitter.inicio.ui.InicioScreen
import com.arquitecturasoftware.twitter.login.LoginViewModel
import com.arquitecturasoftware.twitter.routes.Routes

@Composable
fun LoginScreen(loginViewModel: LoginViewModel, navController: NavController) {
    Box(modifier = Modifier.fillMaxSize().padding(8.dp).imePadding()) {
        val isLoading: Boolean by loginViewModel.isLoading.observeAsState(false)
        val email: String by loginViewModel.email.observeAsState("")
        val password: String by loginViewModel.password.observeAsState("")
        val isLoginEnable: Boolean by loginViewModel.isLoginEnable.observeAsState(false)
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().align(Alignment.Center)) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Header()
                TextoEmail()
                Email(email) { loginViewModel.onLoginChangesEmail(email = it) }
                Spacer(modifier = Modifier.weight(1f))
                HorizontalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))
                Box(modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 48.dp)) {
                    Row (Modifier.padding(start = 16.dp, end = 16.dp)){
                        OlvidarContraseña()
                        Spacer(modifier = Modifier.width(16.dp))
                        SiguienteButtonEmail(isLoginEnable, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen2(loginViewModel: LoginViewModel, navController: NavController){
    Box(modifier = Modifier.fillMaxSize().padding(8.dp).imePadding()) {
        val isLoading: Boolean by loginViewModel.isLoading.observeAsState(false)
        val email: String by loginViewModel.email.observeAsState("")
        val password: String by loginViewModel.password.observeAsState("")
        val isLoginEnable: Boolean by loginViewModel.isLoginEnable.observeAsState(false)
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().align(Alignment.Center)) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Header()
                TextFieldEmail(email)
                Spacer(modifier = Modifier.height(16.dp))
                Password(password) { loginViewModel.onLoginChangesPassword(password = it) }
                Spacer(modifier = Modifier.weight(1f))
                HorizontalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))
                Box(modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 48.dp)) {
                    Row (Modifier.padding(start = 16.dp, end = 16.dp)){
                        OlvidarContraseña()
                        Spacer(modifier = Modifier.width(10.dp))
                        IniciarButton(isLoginEnable, navController)
                    }
                }
            }
        }
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun Header(){
    val activity = LocalContext.current as Activity
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close app",
            modifier = Modifier
                .clickable { activity.finish() }
                .padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
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
fun TextoEmail(){
    Text("Para empezar, introduce tu correo electrónico", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(16.dp), lineHeight = 40.sp)
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
        placeholder = { Text(text = "Password") },
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
fun OlvidarContraseña(){
    TextButton(onClick = {}, border = BorderStroke(1.dp, Color.White), colors = ButtonDefaults.textButtonColors(contentColor = Color.White)) {
        Text("¿Olvidaste tu contraseña?", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Composable
fun SiguienteButtonEmail(loginEnable: Boolean, navController: NavController) {
    Button(onClick = { navController.navigate(Routes.LoginPassword.ruta) }, enabled = loginEnable, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black, disabledContainerColor = Color.LightGray, disabledContentColor = Color.Gray)) {
        Text(text = "Siguiente")
    }
}

@Composable
fun IniciarButton(loginEnable: Boolean, navController: NavController){
    Button(onClick = { navController.navigate(Routes.Inicio.ruta) }, enabled = loginEnable, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black, disabledContainerColor = Color.LightGray, disabledContentColor = Color.Gray)) {
        Text(text = "Iniciar sesión")
    }
}