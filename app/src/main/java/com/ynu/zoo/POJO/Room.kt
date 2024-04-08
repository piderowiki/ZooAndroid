package com.ynu.zoo.POJO

data class Room(
    var id: Int,

    var name:String,

    var type:Int,

    // 你好,你被废弃了
    var onlinePeople:Int,
    // 你也被废
    var isBegin:Boolean,

    // 你好,你也被废弃了,因为我弄错了类型
    // 你俩就准备给personnelList取代吧
    // ready人数准备给HashMap取代
    var readyPeople:Int,
    // 真没想到,连你也有被废弃的一天
    var personnelId:Int,

    var first:Int,

    var time:Int,

    var spare:String,
)