package com.ynu.zoo.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ynu.zoo.POJO.UserName
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM t_userName")
    fun getUserName(): List<UserName>

    @Insert
    fun insertUserName(userName: UserName)

    @Update
    fun update(userName: UserName)
}