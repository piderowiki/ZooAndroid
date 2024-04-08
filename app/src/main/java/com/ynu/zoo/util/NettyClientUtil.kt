package com.ynu.zoo.util

import android.content.ContentValues.TAG
import android.os.Handler
import android.os.Message
import android.util.Log
import com.ynu.zoo.VO.NettyMessagePOJO
import com.ynu.zoo.ZooEngine
import com.ynu.zoo.util.NETTY_SERVER_ADDRESS
import com.ynu.zoo.util.NETTY_SERVER_PORT
import com.ynu.zoo.util.NettyConnect.channel
import io.netty.bootstrap.Bootstrap
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.protobuf.ProtobufDecoder
import io.netty.handler.codec.protobuf.ProtobufEncoder
import java.nio.channels.SocketChannel
import java.util.logging.SocketHandler
import kotlin.math.log

object NettyConnect{
    lateinit var channel:Channel;
    private var eventLoopGroup : NioEventLoopGroup?=null
    private var bootstrap: Bootstrap? = null;
    private var mThread: Thread? = null;
    private var future: ChannelFuture? = null;

    fun nettyConnect(userName:String,roomID:Int,handler: Handler) = try{
        mThread = object : Thread(){
            override fun run(){
                eventLoopGroup = NioEventLoopGroup();
                bootstrap = Bootstrap();
                bootstrap!!.channel(NioSocketChannel::class.java)
                    .group(eventLoopGroup)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(object : ChannelInitializer<io.netty.channel.socket.SocketChannel>(){
                        override fun initChannel(ch: io.netty.channel.socket.SocketChannel?) {
                            var pipeline: ChannelPipeline? = ch?.pipeline();
                            if (pipeline != null) {
                                pipeline.addLast("decoder",ProtobufDecoder(NettyMessagePOJO.NettyMessage.getDefaultInstance()))
                                pipeline.addLast("encoder",ProtobufEncoder())
                            }
                            pipeline?.addLast(nettyMessageHandler(handler))
                        }
                    })
                future = bootstrap!!.connect(NETTY_SERVER_ADDRESS,NETTY_SERVER_PORT).sync();
                if(future?.isSuccess!!){
                    channel = future!!.channel() as Channel
                    Log.i(TAG, "run: Netty连接成功")
                    var messageBody = NettyMessagePOJO.NettyMessage.newBuilder()
                        .setRoomID(roomID)
                        .setMessageType(NETTY_REGISTER_READY).setSender(userName).setRoomID(roomID)
                        .build()
                    channel.writeAndFlush(messageBody)
                }
            }
        }
        mThread!!.start()
    }catch (e:Exception){
        Log.i(TAG, "nettyConnect: 服务器出现异常!!!Netty is Error!!")
        eventLoopGroup?.shutdownGracefully()
    }finally {
        eventLoopGroup?.shutdownGracefully()
    }

    fun groupDestroy(){
        eventLoopGroup?.shutdownGracefully()
    }
}

class nettyMessageHandler(handler:Handler):SimpleChannelInboundHandler<NettyMessagePOJO.NettyMessage>(){
    private var handler = handler
    override fun channelRead0(readCtx: ChannelHandlerContext?, nettyMessage: NettyMessagePOJO.NettyMessage?) {
        Log.i(TAG, "channelRead0: android netty收到消息");
        var handlerChannel:Channel = readCtx!!.channel();
        Log.i(TAG, nettyMessage.toString());

        var message = Message.obtain()
        // 我需要: 棋子移动、User显示、Dialog显示、Text更新
        if(nettyMessage?.messageType == NETTY_SEND_MESSAGE){
            message.what = NETTY_HANDLER_MESSGAE;
            message.data.putString("message",nettyMessage.messageBody);
            message.data.putString("sender",nettyMessage.sender)
            handler.sendMessage(message)
            Log.i(TAG, "转发消息成功,message = ${message.what},sender=${nettyMessage.sender},message=${nettyMessage.messageBody}")

        }else if(nettyMessage?.messageType == NETTY_DIALOG_SHOW){
            message.what = NETTY_HANDLER_DIALOG
            // 这里还得塞错误提示
            handler.sendMessage(message)
        }else if(nettyMessage?.messageType == NETTY_FRESH_USER){
            // 确定先后手情况
            // 先手方会收到代表先手的0,后手方会收到代表后手
            ZooEngine.ImRed = nettyMessage.parameter2 == GAME_FIRST.toInt()
            Log.i(TAG, "因为${nettyMessage.parameter2},将先手情况设置为"+ZooEngine.ImRed)

            if(nettyMessage.parameter3 != null){
                message.data.putString("user2",nettyMessage.parameter3)
                // 收到第二个用户上线的时候,就意味着游戏开始
                if(ZooEngine.ImRed){
                    ZooEngine.key = true
                }
            }
            message.data.putString("user1",nettyMessage.sender)
            message.data.putInt("time",nettyMessage.parameter1)
            message.what = NETTY_HANDLER_FRESH

            handler.sendMessage(message)
        }else if(nettyMessage?.messageType == NETTY_MOVE_ORDER){
            // 我们先获取到要移动的棋子
            ZooEngine.chooseChess = ZooEngine.animalMap[ZooEngine.Position(nettyMessage.parameter1,nettyMessage.parameter2)]
            Log.i(TAG, "netty想要操作${ZooEngine.chooseChess?.name}")
            // 再获取到要移动的位置
            val aimXY = nettyMessage.parameter3
            val aimX = aimXY[0].digitToInt();
            val aimY = aimXY[2].digitToInt();
            // 调用移动
            ZooEngine.tryToMove(aimX,aimY,true)
        }else{
            Log.e(TAG, "消息接收出现问题")
        }
    }
}