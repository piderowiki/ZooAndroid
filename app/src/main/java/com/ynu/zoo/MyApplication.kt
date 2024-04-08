package com.ynu.zoo

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ynu.zoo.DAO.APPDatabase

class MyApplication: Application() {
    companion object {
        lateinit var db: APPDatabase

        @SuppressLint("StaticFieldLeak")
        var sContext: Context? = null

    }

    override fun onCreate() {
        super.onCreate()
        sContext = this

        db = Room
            .databaseBuilder(sContext as MyApplication,APPDatabase::class.java,"db_zoo")
            .build()
    }
}