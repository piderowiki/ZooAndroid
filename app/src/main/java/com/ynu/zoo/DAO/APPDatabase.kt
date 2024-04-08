package com.ynu.zoo.DAO

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.ynu.zoo.POJO.UserName

@Database(entities = [UserName::class], version = 1)
abstract class APPDatabase : RoomDatabase() {
    abstract fun getUserNameDao() : UserDao

    companion object {
        lateinit var instance: APPDatabase

        fun inst(context: Context): APPDatabase {
            if (!::instance.isInitialized) {
                synchronized(APPDatabase::class) {
                    // 创建数据库
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        APPDatabase::class.java,
                        "zoo_database.db")
                        .allowMainThreadQueries() // Room 原则上不允许在主线程操作数据库
                        // 如果要在主线程操作数据库需要调用该函数
                        .build()
                }
            }
            return instance;
        }
    }

}