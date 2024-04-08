package com.ynu.zoo

import android.content.ContentValues.TAG
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.ynu.zoo.VO.NettyMessagePOJO
import com.ynu.zoo.ui.theme.Shapes
import com.ynu.zoo.ui.theme.enemyColor
import com.ynu.zoo.ui.theme.friendColor
import com.ynu.zoo.util.*
import com.ynu.zoo.util.NettyConnect.nettyConnect
import kotlin.math.abs
import kotlin.math.log

// 这里搞一个ViewModel,用来管理查到的Room相关的数据
// 副作用可以调用

// 斗兽棋房间内页面
@Composable
fun ZooGamePage(roomID:String,userName:String,backRoomHall:()->Unit){
    var text by remember {
        mutableStateOf("")
    }
    var chatline:Int = 0;
    var chatBoard by remember {
        mutableStateOf("")
    }

    var user1 by remember {
        mutableStateOf("user1")
    }
    var time by remember {
        mutableStateOf(99)
    }
    var user2 by remember {
        mutableStateOf("user2")
    }

    var openDialog by remember{
        mutableStateOf(false)
    }
    var DialogMsgText by remember{
        mutableStateOf("")
    }

    val handler=object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.i(TAG, "handler接收到消息,message的what = ${msg.what}")
            if(msg.what == NETTY_HANDLER_FRESH){
                // 对三个地方进行更新
                user1 = msg.data.getString("user1").toString()
                user2 = msg.data.getString("user2").toString()
                time = msg.data.getInt("time")

            }else if(msg.what == NETTY_HANDLER_DIALOG){
                DialogMsgText = msg.data.getString("message").toString()
                openDialog = true
            }else if(msg.what == NETTY_HANDLER_MESSGAE){
                Log.i(TAG, "handler的message端也成功接收到了")
                chatline++;
                if(chatline >= 9){
                    chatline = 0;
                    chatBoard = "";
                }
                chatBoard = chatBoard + "\n"+ "  " + msg.data.getString("sender") + ":" + msg.data.getString("message")

            }else if(msg.what == NETTY_HANDLER_MOVE){

            }
        }
    }

    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current){
        "........."
    }.onBackPressedDispatcher
    val backCallback = remember{
        // 创建了一个OnBackPressedCallback回调处理返回键事件
        // 包在remember里是为了使其重组时不要再重复创建
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 处理我们的方法
                // 当然,这里要是能注销也是极好的
            }
        }
    }

    DisposableEffect(true) {
        // 内容就是去查
        nettyConnect(userName,roomID.toInt(),handler)
        // 绑定返回键注销
        backDispatcher.addCallback(backCallback)

        onDispose{backCallback.remove()}
    }


    if(openDialog){
        AlertDialog(onDismissRequest = { // 感觉什么都不用做，反正confirmBtn会带你前往
             },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            title = { Text(text = "消息提示") },
            text = { Text(text = DialogMsgText) },
        confirmButton = {
            Button(onClick = {
                // 分两步,第一步断连接,第二步返回
                // 坏人两边都要做，挂电话两边都要挂
                ZooEngine.reset()
                NettyConnect.groupDestroy()
                backRoomHall()
            }) {
                Text(text = "确认")
            }
        })
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp)) {
        Box(Modifier.fillMaxWidth()) {
            IconButton(modifier = Modifier
                .clip(Shapes.medium)
                .size(40.dp)
                ,onClick = {
                    // 分两步,第一步断连接,第二步返回
                    ZooEngine.reset()
                    NettyConnect.groupDestroy()
                    backRoomHall()
                }) {
                Icon(painter = painterResource(id = R.drawable.return_icon), contentDescription = "")
            }
            
            Text(text = "房间$roomID",modifier = Modifier.align(Alignment.Center))
        }
        Box(Modifier.fillMaxWidth())  {
            Text(text = user1, modifier = Modifier.align(Alignment.CenterStart))
            Text(text = time.toString(), textAlign = TextAlign.Center ,modifier = Modifier
                .align(Alignment.Center)
                .width(30.dp)
                .clip(Shapes.small)
                .border(
                    BorderStroke(1.dp, Color.Black)
                ))
            Text(text = user2, modifier = Modifier.align(Alignment.CenterEnd))
        }
        Spacer(Modifier.height(10.dp))
        // 棋盘,外层的套子用来居中
        Box (Modifier.align(Alignment.CenterHorizontally)){
            ZooEngine.userName = userName
            ZooEngine.roomID   = roomID
            ZooEngine.drawChess()
        }

        
        Spacer(Modifier.height(10.dp))

        Row {
            OutlinedTextField(value = text, onValueChange = {text = it},
            maxLines = 1)
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = {
                val body = NettyMessagePOJO.NettyMessage.newBuilder()
                    .setMessageType(NETTY_SEND_MESSAGE).setSender(userName)
                    .setRoomID(roomID.toInt())
                    .setMessageBody(text).build()
                NettyConnect.channel.writeAndFlush(body)
            }) {
                Text(text = "发送")
            }
        }

        Spacer(Modifier.height(10.dp))

        Text(text = chatBoard,
            Modifier
                .height(150.dp)
                .background(MaterialTheme.colors.surface)
                .border(BorderStroke(1.dp, MaterialTheme.colors.secondary))
                .fillMaxWidth(),
            softWrap = true
        )
    }
}

// 这个是斗兽棋引擎
object ZooEngine {
    // 这个是钥匙
    var key = false
    var userName: String = ""
    var roomID: String   = ""
    var ImRed = true

    // 这是棋子
    data class ChessPiece(
        val name:String,
        val visibel:MutableState<Boolean>,
        val xPosition: MutableState<Int>,
        val yPosition: MutableState<Int>,
        val battlePoint: Int,
        var inTrap:Boolean,
        val image : Int,
        val isFriend : Boolean
    )

    data class Position(
        val x:Int,
        val y:Int,
    )

    val specialMap:Map<Position,String> = mapOf(
        Position(3,1) to "河",
        Position(3,2) to "河",
        Position(3,4) to "河",
        Position(3,5) to "河",
        Position(4,1) to "河",
        Position(4,2) to "河",
        Position(4,4) to "河",
        Position(4,5) to "河",
        Position(5,1) to "河",
        Position(5,2) to "河",
        Position(5,4) to "河",
        Position(5,5) to "河",
        Position(7,3) to "陷阱",
        Position(8,2) to "陷阱",
        Position(8,4) to "陷阱",
        Position(1,3) to "陷阱",
        Position(0,2) to "陷阱",
        Position(0,4) to "陷阱",
        Position(0,3) to "兽穴",
        Position(8,3) to "兽穴",
    )
    var animalMap = mutableMapOf<Position,ChessPiece>()

    var chessList = listOf<ChessPiece>(
        ChessPiece("象", mutableStateOf(true),mutableStateOf(6),mutableStateOf(0),9,false,  R.drawable.elephant_red,true),
        ChessPiece("虎",mutableStateOf(true),mutableStateOf(8),mutableStateOf(0),7,false,  R.drawable.tiger_red,true),
        ChessPiece("狮",mutableStateOf(true),mutableStateOf(8),mutableStateOf(6),8,false,  R.drawable.lion_red,true),
        ChessPiece("豹",mutableStateOf(true),mutableStateOf(6),mutableStateOf(4),6,false,  R.drawable.panther_red,true),
        ChessPiece("狼",mutableStateOf(true),mutableStateOf(6),mutableStateOf(2),5,false,  R.drawable.wolf_red,true),
        ChessPiece("猫",mutableStateOf(true),mutableStateOf(7),mutableStateOf(1),3,false,  R.drawable.cat_red,true),
        ChessPiece("狗",mutableStateOf(true),mutableStateOf(7),mutableStateOf(5),4,false,  R.drawable.dog_red,true),
        ChessPiece("鼠",mutableStateOf(true),mutableStateOf(6),mutableStateOf(6),0,false,  R.drawable.mouse_red,true),

        ChessPiece("象",mutableStateOf(true),mutableStateOf(2),mutableStateOf(6),9,false,  R.drawable.elephant_blue,false),
        ChessPiece("虎",mutableStateOf(true),mutableStateOf(0),mutableStateOf(6),7,false,  R.drawable.tiger_blue,false),
        ChessPiece("狮",mutableStateOf(true),mutableStateOf(0),mutableStateOf(0),8,false,  R.drawable.lion_blue,false),
        ChessPiece("豹",mutableStateOf(true),mutableStateOf(2),mutableStateOf(2),6,false,  R.drawable.panther_blue,false),
        ChessPiece("狼",mutableStateOf(true),mutableStateOf(2),mutableStateOf(4),5,false,  R.drawable.wolf_blue,false),
        ChessPiece("猫",mutableStateOf(true),mutableStateOf(1),mutableStateOf(5),3,false,  R.drawable.cat_blue,false),
        ChessPiece("狗",mutableStateOf(true),mutableStateOf(1),mutableStateOf(1),4,false,  R.drawable.dog_blue,false),
        ChessPiece("鼠",mutableStateOf(true),mutableStateOf(2),mutableStateOf(0),0,false,  R.drawable.mouse_blue,false),
    )

    var chooseChess: ChessPiece? = null;
    private var choosePosition:Position? = null;

    public fun tryToMove(i:Int, j:Int,fromOut:Boolean){
        // 走一步后,把回合计数器重置
        Log.i(TAG, "我获得了行动权")
        key = true

        Log.i(TAG, "当前选中的是${chooseChess?.name},准备移动至$i,$j")
        Log.i(TAG, "被选中的目标位置是${chooseChess!!.xPosition.value},${chooseChess!!.yPosition.value}")

        if(!fromOut){
            val body = NettyMessagePOJO.NettyMessage.newBuilder()
                .setMessageType(NETTY_MOVE_ORDER).setRoomID(roomID.toInt())
                .setSender(userName).setParameter1(chooseChess!!.xPosition.value)
                .setParameter2(chooseChess!!.yPosition.value)
                .setParameter3("$i,$j")
            NettyConnect.channel.writeAndFlush(body)

            Log.i(TAG, "重新还回key")
            key = false
        }

        if(animalMap[Position(i,j)] != null){
            animalMap[Position(i,j)]!!.visibel.value = false
        }

        animalMap.remove(Position(chooseChess!!.xPosition.value, chooseChess!!.yPosition.value))
        if (chooseChess != null) {
            animalMap[Position(i,j)] = chooseChess!!
        }

        if(chooseChess != null){
            choosePosition = Position(i,j)
            chooseChess?.xPosition?.value = i
            chooseChess?.yPosition?.value = j
            chooseChess = null
        }
        chooseChess?.inTrap = (specialMap[Position(i,j)] == "陷阱")

        // 防止可以操作对方棋子
        chooseChess = null
    }

    fun ruleEngineJudge(x:Int,y: Int):Boolean{
        if(chooseChess == null){
            return false
        }
        // 移动需要遵循几个规则
        // 1. 不能移动到河中
        if(specialMap[Position(x,y)] == "河" && chooseChess!!.name != "鼠"){
            return false
        }
        // 2. 移动格子必须相邻(虎，狮跳河除外)
        if((chooseChess!!.name == "狮" || chooseChess !!.name == "虎") &&(abs(chooseChess!!.xPosition.value - x) + abs(chooseChess!!.yPosition.value - y) >= 2)){
            // 是否是跳河判断
            if(chooseChess!!.xPosition.value == x){
                if (chooseChess!!.yPosition.value < y){
                    for (i in chooseChess!!.yPosition.value..y){
                        if(specialMap[Position(x,i)] != "河"){
                            return false
                        }
                    }
                }else{
                    for (i in y downTo chooseChess!!.yPosition.value){
                        if(specialMap[Position(x,i)] != "河"){
                            return false
                        }
                    }
                }

            }else if(chooseChess!!.yPosition.value == y){
                if(chooseChess!!.xPosition.value < x){
                    for (i in chooseChess!!.xPosition.value..x){
                        if(specialMap[Position(i,y)] != "河"){
                            return false
                        }
                    }
                }else{
                    for (i in x downTo chooseChess!!.xPosition.value){
                        if(specialMap[Position(i,y)] != "河"){
                            return false
                        }
                    }
                }

            }else{
                return false
            }
            return true
        }
        if(abs(chooseChess!!.xPosition.value - x) + abs(chooseChess!!.yPosition.value - y) >= 2){
            chooseChess = null;
            return false
        }

        return true
    }

    fun compareBattlePoint(a:Int?,b:Int):Boolean{
        if(a == null){
            return false
        }
        if(a == 0 && b == 9){
            return true
        }else if( a == 9 && b == 0){
            return false
        }else{
            return a >= b
        }
    }

    @Composable
    fun drawChess(){
        Box(
            Modifier
                .width(322.dp)
                .height(414.dp)) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.Center)
                    .background(Color.LightGray)) {
                for (i in 0..9 - 1){
                    Row(Modifier.weight(1.0f)) {
                        for (j in 0..7 - 1){
                            // 文本 in 格子
                            var textInGeZi = ""
                            if(specialMap.containsKey(Position(i,j))){
                                textInGeZi = specialMap[Position(i,j)].toString()
                            }

                            Text(text = textInGeZi,modifier = Modifier
                                .weight(1.0f)
                                .fillMaxHeight()
                                .border(BorderStroke(1.dp, color = Color.Black))
                                .clickable {
                                    Log.i(TAG, "drawChess: 棋盘格子被点击")
                                    if (ruleEngineJudge(i, j)) {
                                        tryToMove(i, j, false)
                                    }
                                })
                        }
                    }
                }
            }
            // 现在要开始想,

            for (piece in chessList){
                animalMap[Position(piece.xPosition.value,piece.yPosition.value)] = piece

                if(piece.visibel.value){
                    IconButton(onClick = {
                        if(!key){
                            Log.i(TAG, "drawChess: 不是你的回合!")
                            return@IconButton
                        }

                        Log.i(TAG, "drawChess: 点击了图标,图标的${piece.isFriend},还有$ImRed")
                        if(piece.isFriend == ImRed){
                            chooseChess = piece
                        }else{
                            if(chooseChess == null){
                                return@IconButton
                            }
                            // 移动阶段
                            if(ruleEngineJudge(piece.xPosition.value,piece.yPosition.value) && (compareBattlePoint(
                                    chooseChess!!.battlePoint,piece.battlePoint)||(piece.inTrap))){
                                tryToMove(piece.xPosition.value,piece.yPosition.value,false)
                            }
                        }

                    }, modifier = Modifier
                        .size(46.dp)
                        .offset(
                            x = (piece.yPosition.value * 46).dp,
                            y = (piece.xPosition.value * 46).dp
                        )) {
                        var chessColor = if(piece.isFriend){
                            friendColor
                        }else{
                            enemyColor
                        }
                        Icon(painter = painterResource(piece.image),"",
                            tint = chessColor)
                    }
                }
            }
        }

    }

    // 无论是handler还是自己走,都要调用这个函数
    // 当退出房间后,就应该调一下这个
    fun reset(){
        ZooEngine.animalMap = mutableMapOf<Position,ChessPiece>()

        ZooEngine.chessList = listOf<ChessPiece>(
            ChessPiece("象", mutableStateOf(true),mutableStateOf(6),mutableStateOf(0),9,false,  R.drawable.elephant_red,true),
            ChessPiece("虎",mutableStateOf(true),mutableStateOf(8),mutableStateOf(0),7,false,  R.drawable.tiger_red,true),
            ChessPiece("狮",mutableStateOf(true),mutableStateOf(8),mutableStateOf(6),8,false,  R.drawable.lion_red,true),
            ChessPiece("豹",mutableStateOf(true),mutableStateOf(6),mutableStateOf(4),6,false,  R.drawable.panther_red,true),
            ChessPiece("狼",mutableStateOf(true),mutableStateOf(6),mutableStateOf(2),5,false,  R.drawable.wolf_red,true),
            ChessPiece("猫",mutableStateOf(true),mutableStateOf(7),mutableStateOf(1),3,false,  R.drawable.cat_red,true),
            ChessPiece("狗",mutableStateOf(true),mutableStateOf(7),mutableStateOf(5),4,false,  R.drawable.dog_red,true),
            ChessPiece("鼠",mutableStateOf(true),mutableStateOf(6),mutableStateOf(6),0,false,  R.drawable.mouse_red,true),

            ChessPiece("象",mutableStateOf(true),mutableStateOf(2),mutableStateOf(6),9,false,  R.drawable.elephant_blue,false),
            ChessPiece("虎",mutableStateOf(true),mutableStateOf(0),mutableStateOf(6),7,false,  R.drawable.tiger_blue,false),
            ChessPiece("狮",mutableStateOf(true),mutableStateOf(0),mutableStateOf(0),8,false,  R.drawable.lion_blue,false),
            ChessPiece("豹",mutableStateOf(true),mutableStateOf(2),mutableStateOf(2),6,false,  R.drawable.panther_blue,false),
            ChessPiece("狼",mutableStateOf(true),mutableStateOf(2),mutableStateOf(4),5,false,  R.drawable.wolf_blue,false),
            ChessPiece("猫",mutableStateOf(true),mutableStateOf(1),mutableStateOf(5),3,false,  R.drawable.cat_blue,false),
            ChessPiece("狗",mutableStateOf(true),mutableStateOf(1),mutableStateOf(1),4,false,  R.drawable.dog_blue,false),
            ChessPiece("鼠",mutableStateOf(true),mutableStateOf(2),mutableStateOf(0),0,false,  R.drawable.mouse_blue,false),
        )
    }

}