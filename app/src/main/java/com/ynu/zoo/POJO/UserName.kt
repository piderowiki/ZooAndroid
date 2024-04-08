package com.ynu.zoo.POJO

import androidx.room.Entity
import androidx.room.PrimaryKey

// 我测,居然是用()定义，太逆天了哥
@Entity(tableName = "t_userName")
data class UserName (
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val name:String = ""
);
