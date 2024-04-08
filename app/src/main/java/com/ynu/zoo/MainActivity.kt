package com.ynu.zoo

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import com.ynu.zoo.DAO.APPDatabase
import com.ynu.zoo.POJO.UserName
import com.ynu.zoo.ui.theme.BloomNewTheme
import com.ynu.zoo.ui.theme.Shapes
import com.ynu.zoo.ui.theme.WelcomeAssets
import com.ynu.zoo.util.*
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import kotlin.concurrent.thread

internal var LocalWelcomeAssets = staticCompositionLocalOf {
    WelcomeAssets.LightWelcomeAssets as WelcomeAssets
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window,false)

        setContent {
            BloomNewTheme(false) {
                rememberSystemUiController().setStatusBarColor(
                    Color.Transparent, darkIcons = MaterialTheme.colors.isLight)
                Column {
                    // 这里
                    Spacer(modifier = Modifier.height(22.dp))
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        AppNavigation()
                        //ZooGamePage(1)
                    }
                }
            }
        }
    }



}



val address = "http://${NETTY_SERVER_ADDRESS}:8081/zoo/vistor/logIn/"
@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "login") {
        //声明名为MainPage的页面路由
        composable("GameChoosePage/{userName}"){
            //页面路由对应的页面组件
            val inUserName: String = it.arguments?.getString("userName").toString();
            GameChoosePage({navController.popBackStack()},inUserName){userName: String, gameType: Int ->
                navController.navigate("RoomPage/$userName/$gameType"){
                    popUpTo("GameChoosePage")
                }
            }
            // 这边加一个提示,可以提示自动登录失败
        }
        composable("login"){
            LoginPage(){userName ->
                // 去访问User登录,然后看看
                navController.navigate("GameChoosePage/$userName"){
                    popUpTo("login")
                }
            }
        }
        composable("RoomPage/{userName}/{gameType}"){
            val inUserName: String = it.arguments?.getString("userName").toString();
            val inGameType: String = it.arguments?.getString("gameType").toString();
            RoomPage(gameType = inGameType, userName = inUserName,
                backFun = {navController.popBackStack()}){roomID->
                navController.navigate("ZooGamePage/${roomID}/${inUserName}"){
                    popUpTo("RoomPage")
                }
            }
        }
        composable("ZooGamePage/{roomID}/{userName}"){
            val inRoomID  : String  = it.arguments?.getString("roomID").toString();
            val inUserName: String = it.arguments?.getString("userName").toString();
            ZooGamePage(inRoomID,inUserName){
                // 这里要做到删除原本的,再进入RoomHall
                //navController.previousBackStackEntry
                navController.popBackStack()
            }
        }

    }
}

@Composable
fun Toast(message: String) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(text = message, color = Color.White, fontSize = 16.sp)
    }
}


@Composable
// 给你做一个默认登录效果
fun LoginPage(toGameChoosePage: (userName:String) -> Unit){
    val handler=object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if(msg.what == 100){
                msg.data.getString("userName")?.let { toGameChoosePage(it) }
            }
        }
    }

    val userNameDao = MyApplication.db.getUserNameDao()

    var text by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }
    var isError  by remember { mutableStateOf(false)}
    var showToast by remember { mutableStateOf(false)}

    if(showToast){
        Toast("自动登录失败，请重新登录")
    }

    // 这段副作用代码用于默认登录作用
    /*
    DisposableEffect(true){
        thread {
            var userNameList= userNameDao.getUserName()
            if(userNameList.size != 0) {
                HttpUtil.sendOKHttpRequest(address, object : okhttp3.Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        // 虽然自动登录失败
                        showToast = true
                    }

                    override fun onResponse(call: Call, response: Response) {
                        var responseData = response.body?.string()
                        if (responseData != null) {
                            var zooMsg = Gson().fromJson(responseData, ZooMessage::class.java)
                            if (zooMsg.successFlag == MESSAGE_SUCCESS) {
                                // 把结果存在本地
                                // 最好开个线程
                                toGameChoosePage();
                            }
                        }
                    }
                })
            }
        }.start()
        onDispose {}
    }
    */
    // 要求往里收缩16dp
    Column(
        // LoginTitle
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp)) {
        // 可以分成四个部分，但是也可以稍微省省
        Text(text = "游客登录-请输入用户名",
            style = MaterialTheme.typography.h1,color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .paddingFromBaseline(top = 184.dp, bottom = 16.dp),
            textAlign = TextAlign.Center)

        // LoginInputBox
        // 注意，这里都是不能输入的，只是装饰用
        OutlinedTextField(value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(shape = Shapes.small),
            isError = isError,
            label = {Text("用户名")},
            placeholder = {
                Text(text = errorMsg,
                    style = MaterialTheme.typography.body1,color = MaterialTheme.colors.onPrimary)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        //HintWithUnderline
        HintWithUnderline()

        // log in Button
        Button(onClick = {
            // 此处需要其它业务判断
            if(text == null || text == ""){
                isError = true;
                errorMsg = "用户名不可以为空";
                return@Button;
            }

            // 准备用OKHttpUtil发送请求
            HttpUtil.sendOKHttpRequest(address + text,object :okhttp3.Callback{
                override fun onFailure(call: Call, e: IOException) {
                    isError = true;
                    errorMsg = "网络出错请重试"
                    text = ""
                    Log.i(TAG, "onFailure: ${e.message}")
                    return;
                }

                override fun onResponse(call: Call, response: Response) {
                    var responseData = response.body?.string()

                    if(responseData == null){
                        isError = true;
                        errorMsg = "network出错请重试"
                        text = ""
                        return;
                    }
                    var zooMsg = Gson().fromJson(responseData,ZooMessage::class.java)

                    if(zooMsg.successFlag == MESSAGE_FAILED){
                        isError = true;
                        errorMsg = "用户不能重复登录"
                        text = ""
                        return;
                    }else if(zooMsg.successFlag == MESSAGE_SUCCESS){
                        // 把结果存在本地
                        // 最好开个线程
                        Thread{
                            var userNameList= userNameDao.getUserName()
                            if(userNameList.size != 0){
                                userNameDao.update(UserName(0,text))
                            }else{
                                userNameDao.insertUserName(UserName(0,text))
                            }
                        }.start()

                        var message = Message.obtain()
                        message.what = 100
                        message.data.putString("userName",text)
                        handler.sendMessage(message)
                    }
                }
            })
                         },
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .clip(shape = Shapes.medium),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)) {

            Text(text = "登  录",style = MaterialTheme.typography.button,color = MaterialTheme.colors.onSecondary)
        }
    }
}

@Composable
fun HintWithUnderline(){
    Column(Modifier.paddingFromBaseline(top = 24.dp, bottom = 16.dp)) {
        TopText()
        BottomText()
    }
}

@Composable
fun TopText(){
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        var wordPre = "By clicking below you agree to our".split(" ")
        var wordPost= "and consent".split(" ")
        for (word in wordPre){
            Text(text = word, style = MaterialTheme.typography.body2, color = MaterialTheme.colors.onPrimary)
        }
        Text(text = "Terms of Use",style = MaterialTheme.typography.body2,color = MaterialTheme.colors.onPrimary,
            textDecoration = TextDecoration.Underline)
        for (word in wordPost){
            Text(text = word, style = MaterialTheme.typography.body2, color = MaterialTheme.colors.onPrimary)
        }
    }
}

@Composable
fun BottomText(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = " to our ",style = MaterialTheme.typography.body2,color = MaterialTheme.colors.onPrimary)
        Text(text = "Privacy Policy.",style = MaterialTheme.typography.body2,color = MaterialTheme.colors.onPrimary,
            textDecoration = TextDecoration.Underline)
    }
}