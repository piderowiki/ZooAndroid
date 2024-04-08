package com.ynu.zoo

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
// 第一个msg是要传入的状态
fun ComposeMenu(msg:String,msg1:String,msg2:String,msg3:String,changeMsg:(msg:String)->Unit){
    var expanded by remember {
        mutableStateOf(false)
    }

    Log.i(TAG, "ComposeMenu: $expanded")
    Box(Modifier.width(100.dp)){
        Text(text = msg, textAlign = TextAlign.Center, modifier = Modifier
            .width(50.dp)
            .clip(MaterialTheme.shapes.small)
            .border(BorderStroke(1.dp, Color.Black))
            .align(Alignment.CenterStart)
            .clickable { expanded = true })

        IconButton(onClick ={ expanded = true}, modifier = Modifier
            .padding(horizontal = 16.dp)
            .align(Alignment.BottomEnd)){
            Icon(Icons.Default.MoreVert, contentDescription ="menu" )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {  expanded = false },
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .wrapContentSize(Alignment.TopStart)
        ) {
            DropdownMenuItem(onClick = { changeMsg(msg1)
                expanded = false}) {
                Text(text = msg1)
            }
            DropdownMenuItem(onClick =  { changeMsg(msg2)
                expanded = false}) {
                Text(text = msg2)
            }
            DropdownMenuItem(onClick =  { changeMsg(msg3)
                expanded = false}) {
                Text(text = msg3)
            }
        }

    }
}