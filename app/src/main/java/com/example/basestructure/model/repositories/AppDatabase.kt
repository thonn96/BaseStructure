package com.example.basestructure.model.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.basestructure.model.daos.UserDao
import com.example.basestructure.model.entities.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [User::class], version = 7, exportSchema = true)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "Data.db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        // Since this should only be called when the app is restarted, all
                        // all child processes should have been killed and sessions should be
                        // inactive.
                        GlobalScope.launch { getInstance(context).userDao().resetUserStatus() }
                    }
                })
                .build()
    }
}