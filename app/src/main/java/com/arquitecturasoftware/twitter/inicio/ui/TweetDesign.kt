package com.arquitecturasoftware.twitter.inicio.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.arquitecturasoftware.twitter.R

@Composable
fun TweetDesign(){
    var chat by rememberSaveable { mutableStateOf(false) }
    var rt by rememberSaveable { mutableStateOf(false) }
    var like by rememberSaveable { mutableStateOf(false) }
    Column {
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
                Row(Modifier.padding(top = 16.dp)){
                    SocialIcon(modifier = Modifier.weight(1f), unselectedIcon = {
                        Icon(
                            painterResource(R.drawable.ic_chat), contentDescription = "", tint = Color(0xFF7E8B98)
                        )
                    }, selectedIcon = {
                        Icon(
                        painterResource(R.drawable.ic_chat_filled), contentDescription = "", tint = Color.Red)
                    }, isSelected = chat) { chat = !chat }
                    SocialIcon(modifier = Modifier.weight(1f), unselectedIcon = {
                        Icon(
                            painterResource(R.drawable.ic_rt), contentDescription = "", tint = Color(0xFF7E8B98)
                        )
                    }, selectedIcon = {
                        Icon(
                        painterResource(R.drawable.ic_rt), contentDescription = "", tint = Color.Green)
                    }, isSelected = rt) { rt = !rt }
                    SocialIcon(modifier = Modifier.weight(1f), unselectedIcon = {
                        Icon(
                            painterResource(R.drawable.ic_like), contentDescription = "", tint = Color(0xFF7E8B98)
                        )
                    }, selectedIcon = {
                        Icon(
                        painterResource(R.drawable.ic_like_filled), contentDescription = "", tint = Color.Red)
                    }, isSelected = like) { like = !like }
                }
            }
        }
        HorizontalDivider(color = Color.Gray, thickness = 1.dp)
    }
}

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
