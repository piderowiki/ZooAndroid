package com.ynu.zoo

import android.content.ContentValues.TAG
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.SystemClock.sleep
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.google.accompanist.insets.statusBarsHeight
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ynu.zoo.POJO.RoomVO
import com.ynu.zoo.ui.theme.BloomNewTheme
import com.ynu.zoo.ui.theme.Shapes
import com.ynu.zoo.util.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.math.log

class SearchTextViewModel : ViewModel(){
    private var _text =  mutableStateOf("")
    val text :State<String> = _text

    fun changeText(newMsg:String){
        _text.value = newMsg;
    }
}

class ShowToastViewModel : ViewModel(){
    var showToast = mutableStateOf(false)

    fun changeShowToast(show:Boolean){
        showToast.value = show
    }
}

class TimeChooseViewModel : ViewModel(){
    var timeChoose = mutableStateOf("15")

    fun changeText(newMsg:String){
        timeChoose.value = newMsg;
    }
}

class FirstChooseViewModel : ViewModel(){
    var firstChoose = mutableStateOf("猜先")

    fun changeText(newMsg:String){
        firstChoose.value = newMsg;
    }
}

class CreateRoomModel:ViewModel(){
    // 把handler传入
    lateinit var handler:Handler
    val use = "http://${NETTY_SERVER_ADDRESS}:8081"
    val url = "$use/zoo/create/room"
    // 不需要监视
    lateinit var responseData:String

    fun sendCreateRoomRequestGetUser(room: RoomVO) {
        val header = "application/json;charset=utf-8".toMediaType();
        val newBody = Gson().toJson(room).toRequestBody(header)

        val client = OkHttpClient().newBuilder()
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .build()
        try {
            val request = Request.Builder()
                .url(url)
                .post(newBody)
                .build()
            val response = client.newCall(request).execute()
            responseData = response.body!!.string()
            // Gson后,拿到roomID,然后去访问
            var zooMsg = Gson().fromJson(responseData,ZooMessage::class.java)

            inRoom(room.masterName,zooMsg.messageBody.orEmpty(),handler)
        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
    }
}


@Composable
fun ToastInRoomPage(message: String) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(text = message, color = Color.White, fontSize = 16.sp)
    }
}

class RoomListViewModel : ViewModel(){
    // 似乎mutableStatelistOf对list的内部增删更加敏感。
    // 整体替换则传统方法明显更佳
    var roomList: List<com.ynu.zoo.POJO.Room> by mutableStateOf(listOf<com.ynu.zoo.POJO.Room>(

        )
    )

    fun roomFresh(newRoomList:List<com.ynu.zoo.POJO.Room>){
        roomList = newRoomList
    }

    fun getNewRoomList():List<com.ynu.zoo.POJO.Room>{
        return roomList.toList()
    }
}

@Composable
fun RoomPage(userName:String,gameType:String,backFun:() -> Unit,toGame:(roomID:String) ->Unit){
    val searchTextViewModel:SearchTextViewModel = viewModel();
    val roomListViewModel:RoomListViewModel = viewModel();
    val showToastViewModel:ShowToastViewModel = viewModel();
    val firstChooseViewModel:FirstChooseViewModel = viewModel()
    val timeChooseViewModel:TimeChooseViewModel = viewModel()
    val createRoomModel:CreateRoomModel = viewModel()

    val handler=object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if(msg.what == 101){
                searchTextViewModel.changeText("")
            }else if(msg.what == 200){
                Log.i(TAG, "handleMessage: 进入了房间里")
                msg.data.getString("roomID")?.let { toGame(it) }
            }
        }
    }
    createRoomModel.handler = handler
    
    var openDialog = remember {
        mutableStateOf(false)
    }
    var dialogText by remember {
        mutableStateOf(userName +"的房间")
    }

    val dialogWidth = 300.dp
    val dialogHeight= 300.dp
    if(openDialog.value){
        Dialog(onDismissRequest = {
            openDialog.value = false
        }) {
                Column(
                    Modifier
                        .width(dialogWidth)
                        .height(dialogHeight)
                        .background(MaterialTheme.colors.background)
                        .padding(8.dp)
                        ) {
                    Text(text = "房间名字", maxLines = 1)
                    TextField(value = dialogText, onValueChange = {dialogText = it}, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(5.dp))
                    Text(text = "先手情况")
                    ComposeMenu(firstChooseViewModel.firstChoose.value,"先手","让先","猜先",firstChooseViewModel::changeText)
                    Spacer(Modifier.height(5.dp))
                    Text(text = "回合时长")
                    ComposeMenu(timeChooseViewModel.timeChoose.value +"秒","15","30","60",timeChooseViewModel::changeText)
                    Button(onClick = {
                        var first = ""
                        if(firstChooseViewModel.firstChoose.value == "先手"){
                            first = GAME_FIRST
                        }else if(firstChooseViewModel.firstChoose.value == "让先"){
                            first = GAME_SECOND
                        }else{
                            first = GAME_GUESS
                        }

                        val room = RoomVO(dialogText,first,timeChooseViewModel.timeChoose.value,gameType,userName)
                        Thread{
                            createRoomModel.sendCreateRoomRequestGetUser(room)
                            if(createRoomModel.responseData!=null){
                                var createMsg = Gson().fromJson(createRoomModel.responseData,ZooMessage::class.java)
                                if(createMsg.successFlag == MESSAGE_SUCCESS){
                                    handler.sendEmptyMessage(200)
                                }
                            }
                        }.start()

                        openDialog.value = false
                    }) {
                        Text(text = "提交")
                    }
                }

        }
    }

    DisposableEffect(true) {
        // 内容就是去查
        getAllRoom(showToastViewModel::changeShowToast,gameType,roomListViewModel::roomFresh)
        onDispose{}
    }

    Box(Modifier.fillMaxSize()) {
        if(showToastViewModel.showToast.value){
            Toast(message = "出错,请重试!")

            // 展示两秒
            Thread{
                sleep(2 * 1000)
                showToastViewModel.showToast.value = false
            }.start()
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)) {
            IconButton(modifier = Modifier
                .clip(Shapes.medium)
                .size(40.dp)
                ,onClick = { backFun() }) {
                Icon(painter = painterResource(id = R.drawable.return_icon), contentDescription = "")
            }
            Spacer(modifier = Modifier
                .height(5.dp)
                .background(MaterialTheme.colors.background)
                .fillMaxWidth())
            SearchBar(roomListViewModel::roomFresh,showToastViewModel::changeShowToast
                ,gameType,searchTextViewModel.text.value,searchTextViewModel::changeText)
            RoomList(userName,handler,roomListViewModel.roomList)
        }

        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .padding(PaddingValues(bottom = 56.dp))
            , horizontalArrangement = Arrangement.End) {
            FloatingActionButton(onClick = { openDialog.value = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription ="")
            }
            Spacer(modifier = Modifier.width(10.dp))
            FloatingActionButton(onClick = { getAllRoom(showToastViewModel::changeShowToast,gameType,roomListViewModel::roomFresh)
                Log.i(TAG, "RoomPage:${roomListViewModel.getNewRoomList()}")
                handler.sendEmptyMessage(101)
            })
                {
                Icon(imageVector = Icons.Default.Refresh, contentDescription ="")
            }
        }
    }
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun SearchBar(freshList: (List<com.ynu.zoo.POJO.Room>) -> Unit, showToastChange:(show:Boolean)->Unit, gameType: String, text:String, changeText:(newText:String) -> Unit){
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(value = text, onValueChange ={changeText(it)},
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(MaterialTheme.shapes.small)
            .border(BorderStroke(1.dp, Color.Gray)),
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search,
                contentDescription = "", modifier = Modifier.size(18.dp),)
        }, maxLines = 1, keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        , keyboardActions = KeyboardActions(onDone = {
            // 也许应该发一个msg到外部,由外部线程负责处理
            getRoomByName(showToastChange,text,gameType,freshList)
            keyboardController?.hide()
        }),
        placeholder = {
            Text(text = "请输入房间名",style = MaterialTheme.typography.body1,color = MaterialTheme.colors.onPrimary)
        }
    )
}

@Composable
fun RoomList(userName: String,handler: Handler,roomList:List<com.ynu.zoo.POJO.Room>){
    // 来点距离
    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(20.dp))
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()){
        items(roomList.size, key =  {roomList[it].id}){
            Spacer(Modifier.height(8.dp))
            RoomCard(userName = userName, handler = handler,room = roomList[it])
        }
    }
}

@Composable
fun RoomCard(userName: String,handler: Handler,room:com.ynu.zoo.POJO.Room){
    var textBegin = "";
    if(room.isBegin){
        textBegin = "已开始"
    }else{
        textBegin = "未开始"
    }
    //------------------
    var maxPeople = 0;
    if(room.type == GAME_ZOO_TYEP){
        maxPeople = 2;
    }

    Card(shape = Shapes.small,elevation = 4.dp,modifier = Modifier
        .height(100.dp)
        .fillMaxWidth()
        .clickable {
             // 准备去访问,还需要一个RoomID
            inRoom(userName,room.id.toString(),handler)
        },
    ) {
        Row(
            Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(8.dp)) {
            Column(
                Modifier
                    .weight(2.0f)
                    .fillMaxHeight()) {
                Text(fontSize = 20.sp,text = room.name, modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp))
                Row(modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "")
                    Text(text = "${room.onlinePeople}/$maxPeople")
                }
            }
            Text(text = textBegin,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1.0f)
            )
        }
    }
}



fun getAllRoom(showToast:(Boolean) -> Unit,gameType:String,freshList:(List<com.ynu.zoo.POJO.Room>) -> Unit){
    val address = "$SERVER_ADDRESS/zoo/get/all/room/$gameType"
    HttpUtil.sendOKHttpRequest(address,object :okhttp3.Callback{
        override fun onFailure(call: Call, e: IOException) {
            Log.i(TAG, "onFailure: network is wrong")
            showToast(true)
        }

        override fun onResponse(call: Call, response: Response) {
            var responseData = response.body?.string()
            Log.i(TAG,responseData.toString())
            if(!response.isSuccessful || responseData == null){
                Log.i(TAG,"return Msg is null")
                showToast(true)
                return;
            }
            Log.i(TAG,"return Msg is not null")
            //showToast(true)

            var roomList:List<com.ynu.zoo.POJO.Room> =
                Gson().fromJson(responseData, object :
                    TypeToken<List<com.ynu.zoo.POJO.Room>>(){}.type)
            freshList(roomList)
        }

    })
}
//,searchRoomByName:(text:String,gameType:String) -> Unit
fun getRoomByName(showToast:(Boolean) -> Unit,roomName:String,gameType:String,freshList:(List<com.ynu.zoo.POJO.Room>) -> Unit){
    val address = "$SERVER_ADDRESS/zoo/get/room/name/$roomName/$gameType"
    HttpUtil.sendOKHttpRequest(address,object :okhttp3.Callback{
        override fun onFailure(call: Call, e: IOException) {
            Log.i(TAG, "onFailure: network is wrong")
            showToast(true)
        }

        override fun onResponse(call: Call, response: Response) {
            var responseData = response.body?.string()
            Log.i(TAG,responseData.toString())
            if(!response.isSuccessful || responseData == null){
                Log.i(TAG,"return Msg is null")
                showToast(true)
                return;
            }
            Log.i(TAG,"return Msg is not null")
            //showToast(true)

            var roomList:List<com.ynu.zoo.POJO.Room> =
                Gson().fromJson(responseData, object :
                    TypeToken<List<com.ynu.zoo.POJO.Room>>(){}.type)
            freshList(roomList)
        }
    })
}

fun inRoom(userName:String,roomID: String,handler: Handler){
    val address = "$SERVER_ADDRESS/zoo/in/room/$roomID/$userName"
    HttpUtil.sendOKHttpRequest(address,object :okhttp3.Callback{
        override fun onFailure(call: Call, e: IOException) {
        }

        override fun onResponse(call: Call, response: Response) {
            var responseData = response.body?.string()
            Log.i(TAG,responseData.toString())
            if(!response.isSuccessful || responseData == null){
                Log.i(TAG,"return Msg is null")

                return;
            }
            var zooMsg = Gson().fromJson(responseData,ZooMessage::class.java)
            if(zooMsg.successFlag == MESSAGE_SUCCESS){
                // 成功的情况,我们就可以准备前往房间了
                var message = Message.obtain()
                message.what = 200
                message.data.putString("roomID",roomID)
                handler.sendMessage(message)
            }else{

                return;
            }
        }
    })

}
