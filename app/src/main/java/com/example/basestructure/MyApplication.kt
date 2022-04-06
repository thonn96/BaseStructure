package com.example.basestructure

import android.app.Application
import android.content.Context
import com.example.basestructure.model.repositories.AppDatabase

class MyApplication : Application(){
    companion object {
        var ctx: Context? = null
        fun getContext() : Context{
            return ctx!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext

        val ulaDatabase = AppDatabase.getInstance(this)

    }


}