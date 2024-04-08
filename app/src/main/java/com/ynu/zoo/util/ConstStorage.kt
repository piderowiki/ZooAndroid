package com.ynu.zoo.util
const val NETTY_SERVER_ADDRESS = "192.168.235.97"
const val NETTY_SERVER_PORT = 9999
const val SERVER_ADDRESS = "http://192.168.235.97:8081"

const val USER_IN_HALL = 0
const val ROOM_NEED_RANDOM_FIRST = 9

// 这里是Error消息
const val MESSAGE_SUCCESS = 1
const val MESSAGE_FAILED = 0

// 这里是游戏人数对应
const val GAME_ZOO_PEOPLE_MAX = 2
const val GAME_ZOO_TYEP = 1
const val GAME_FIRST = "0"
const val GAME_SECOND = "1"
const val GAME_GUESS = "9"

// 这里是MQ处理消息
const val MQ_DELETE_ROOM = "deleteRoom"

// 这是Netty部分的消息
const val NETTY_REGISTER_READY = -1
const val NETTY_SEND_MESSAGE = 0
const val NETTY_FRESH_USER = 1
const val NETTY_DIALOG_SHOW = 2
const val NETTY_MOVE_ORDER = 3

const val NETTY_HANDLER_MESSGAE = 900
const val NETTY_HANDLER_FRESH = 800
const val NETTY_HANDLER_DIALOG = 700
const val NETTY_HANDLER_MOVE = 600