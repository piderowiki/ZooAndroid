package com.ynu.zoo.POJO

import com.ynu.zoo.util.GAME_ZOO_TYEP

data class RoomVO (
    var roomName: String = "zpj的房间",
    var first : String= "0",
    var time: String = "15",
    var type : String= GAME_ZOO_TYEP.toString(),
    var masterName: String = "zpj"
)