package com.ynu.zoo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsHeight
import com.ynu.zoo.ui.theme.BloomNewTheme
import com.ynu.zoo.ui.theme.Shapes
import com.ynu.zoo.util.GAME_ZOO_TYEP

@Composable
fun GameChoosePage(backFun:() -> Unit,userName:String,toRoomHall:(userName:String,gameType:Int) -> Unit){
    Column(Modifier.fillMaxSize()) {
        IconButton(modifier = Modifier
            .clip(Shapes.medium)
            .size(40.dp)
            ,onClick = { backFun() }) {
            Icon(painter = painterResource(id = R.drawable.return_icon), contentDescription = "")
        }

        Spacer(modifier = Modifier
            .height(10.dp)
            .fillMaxWidth())
        
        Card(
            shape = Shapes.small,
            elevation = 10.dp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .width(300.dp)
                .height(100.dp)
        ) {
            Box(Modifier.fillMaxSize()) {

                Button(modifier = Modifier.fillMaxSize(), onClick = { toRoomHall(userName,GAME_ZOO_TYEP) }) {
                    Text(text = "斗 兽 棋", style = MaterialTheme.typography.h1)
                }
                Image(painter = painterResource(R.mipmap.zoo) , contentDescription = "",
                    Modifier.alpha(0.3f).fillMaxSize(), contentScale = ContentScale.FillBounds)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DarkPreview(){
    BloomNewTheme(false) {
        GameChoosePage({},""){it1,it2 ->

        }
    }
}