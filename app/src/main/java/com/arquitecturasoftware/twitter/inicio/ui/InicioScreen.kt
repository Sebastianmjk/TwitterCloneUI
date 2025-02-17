package com.arquitecturasoftware.twitter.inicio.ui

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CircleNotifications
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.arquitecturasoftware.twitter.R
import com.arquitecturasoftware.twitter.routes.Routes

//Funcion que contiene el diseño de la pantalla de inicio
@Composable
fun InicioScreen(navController: NavController) {
    DisableBackPressHandler()
    Scaffold(
        topBar = {
            HeaderInicio(onCliclkIcon = { /*inicioViewModel.onDialogOpened()*/ })
        },
        bottomBar = {
            MyBottomNavigationInicio(navController)
        },
        floatingActionButton = {
            Fab(onAbrirMenu = { navController.navigate(Routes.AddTweet.ruta) })
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TweetDesign()
        }
    }
}

//------------------- Screen principal del inicio -------------------//
@Composable
fun HeaderInicio(onCliclkIcon:(String) -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "profile picture",
            modifier = Modifier.clip(shape = CircleShape).size(55.dp)
        )
        Spacer(modifier = Modifier.weight(0.9f))
        Image(
            painter = painterResource(id = R.drawable.logo_twitter),
            contentDescription = "profile picture",
            modifier = Modifier.clip(shape = CircleShape).size(55.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onCliclkIcon ("Configuracion") }, colors = IconButtonDefaults.iconButtonColors(
            contentColor = Color.White
        )) {
            Icon(imageVector = Icons.Filled.CircleNotifications, contentDescription = "Configuracion")
        }
    }
}

@Composable
fun MyBottomNavigationInicio(navController: NavController) {
    var indexMio by remember { mutableIntStateOf(0) }
    NavigationBar(containerColor = Color.Black) {
        NavigationBarItem(
            selected = indexMio == 0,
            onClick = { indexMio = 0
                navController.navigate(Routes.Inicio.ruta) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "home"
                )
            }, label = {
                Text(text = "Inicio", color = Color.White) },
            colors = NavigationBarItemDefaults.colors(Color.White)
        )
        NavigationBarItem(
            selected = indexMio == 1,
            onClick = { indexMio = 1 },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "person"
                )
            }, label = { Text(text = "Perfil", color = Color.White) },
            colors = NavigationBarItemDefaults.colors(Color.White)
        )
    }
}

@Composable
fun Fab(onAbrirMenu: ()->Unit) {
    FloatingActionButton(onClick = { onAbrirMenu() },
        containerColor = Color.Blue,
        contentColor = Color.White

    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
    }
}

//Funcion que contiene el diseño de la pantalla de añadir tweet
@Composable
fun AddTweet(navigationController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        HeaderAddTweet(Modifier.align(Alignment.Start), navigationController)
        Spacer(modifier = Modifier.size(4.dp))
        Tweetear(Modifier.align(Alignment.CenterHorizontally))
    }
}

//------------------- Screen de añadir tweet -------------------//
@Composable
fun Tweetear(modifier: Modifier) {
    var tweet by remember { mutableStateOf("") }
    Row(modifier.fillMaxWidth().padding(16.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "profile picture",
            modifier = Modifier.clip(shape = CircleShape).size(55.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        TextField(
            value = tweet,
            onValueChange = { tweet = it },
            maxLines = 12,
            label = { Text("¿Qué está pensando?") },
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                unfocusedTextColor = Color.LightGray,
                focusedTextColor = Color.Black
            )
        )
    }
}

@Composable
fun HeaderAddTweet(modifier: Modifier = Modifier, navController: NavController) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close app",
            modifier = Modifier
                .clickable { navController.popBackStack() }
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { },
            modifier = Modifier
                .weight(1f)
        ) {
            Text("Publicar")
        }
    }
}

//Funcion que contiene el diseño de un tweet
@Composable
fun TweetDesign(){
    var chat by remember { mutableStateOf(false) }
    var rt by remember { mutableStateOf(false) }
    var like by remember { mutableStateOf(false) }
    Column (modifier = Modifier.padding(top = 16.dp)){
        HorizontalDivider(color = Color.Gray, thickness = 1.dp)
        Row(Modifier.fillMaxWidth().padding(10.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "profile picture",
                modifier = Modifier.clip(shape = CircleShape).size(55.dp)
            )
            Column (Modifier.fillMaxWidth().padding(16.dp)){
                Row(Modifier.fillMaxWidth()){
                    TextTitle("Aris", Modifier.padding(end = 8.dp))
                    DefaultTitle("@AristiDevs", Modifier.padding(end = 8.dp))
                    DefaultTitle("4h", Modifier.padding(end = 8.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(painterResource(id = R.drawable.ic_dots), contentDescription = "dots", tint = Color.White)
                }
                TextBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet.", Modifier.padding(bottom = 16.dp))
                Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "profile", modifier = Modifier.fillMaxWidth().height(200.dp).clip(
                    RoundedCornerShape(10)
                ), contentScale = ContentScale.Crop)
                Row(Modifier.padding(top = 16.dp)){
                    SocialIcon(modifier = Modifier.weight(1f), unselectedIcon = {Icon(painterResource(R.drawable.ic_chat), contentDescription = "", tint = Color(0xFF7E8B98))}, selectedIcon = {Icon(
                        painterResource(R.drawable.ic_chat_filled), contentDescription = "", tint = Color.Red)}, isSelected = chat) { chat = !chat }
                    SocialIcon(modifier = Modifier.weight(1f), unselectedIcon = {Icon(painterResource(R.drawable.ic_rt), contentDescription = "", tint = Color(0xFF7E8B98))}, selectedIcon = {Icon(
                        painterResource(R.drawable.ic_rt), contentDescription = "", tint = Color.Green)}, isSelected = rt) { rt = !rt }
                    SocialIcon(modifier = Modifier.weight(1f), unselectedIcon = {Icon(painterResource(R.drawable.ic_like), contentDescription = "", tint = Color(0xFF7E8B98))}, selectedIcon = {Icon(
                        painterResource(R.drawable.ic_like_filled), contentDescription = "", tint = Color.Red)}, isSelected = like) { like = !like }
                }
            }
        }
        HorizontalDivider(color = Color.Gray, thickness = 1.dp)
    }
}

//------------------- Componentes del diseño del tweet-------------------//
@Composable
fun SocialIcon(modifier: Modifier, unselectedIcon: @Composable () -> Unit, selectedIcon: @Composable () -> Unit, isSelected: Boolean, onItemSelected: () -> Unit) {
    val defaultValue = 1
    Row(modifier = modifier.clickable { onItemSelected() }, verticalAlignment = Alignment.CenterVertically) {
        if (isSelected) {
            selectedIcon()
        } else {
            unselectedIcon()
        }
        Text(text = if(isSelected) (defaultValue+1).toString() else defaultValue.toString(), color = Color(0xFF7E8B98), fontSize = 12.sp, modifier = Modifier.padding(start = 4.dp))
    }

}

@Composable
fun TextBody(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text, color = Color.White,  modifier = modifier
    )
}

@Composable
fun TextTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title, color = Color.White, fontWeight = FontWeight.ExtraBold, modifier = modifier
    )
}

@Composable
fun DefaultTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title, color = Color.Gray,  modifier = modifier
    )
}

@Composable
fun TweetsList(addTweetViewModel: AddTweetViewModel){
    LazyColumn {
//        TweetDesign()
    }
}

@Composable
fun DisableBackPressHandler() {
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do nothing to disable back press
            }
        }
    }

    DisposableEffect(backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}