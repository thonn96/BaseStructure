package com.example.basestructure.model.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.basestructure.model.entities.StatusType
import com.example.basestructure.model.entities.User

@Dao
interface UserDao {
    @Query("select * from users")
    fun getAllUser() : LiveData<List<User>>
    @Query("update users set status = 'offline' ")
    fun resetUserStatus()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)
}