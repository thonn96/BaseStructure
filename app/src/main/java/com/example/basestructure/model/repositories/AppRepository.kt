package com.example.basestructure.model.repositories

import android.media.session.MediaSessionManager
import androidx.lifecycle.LiveData
import com.example.basestructure.model.daos.UserDao
import com.example.basestructure.model.entities.User
import com.example.basestructure.model.preference.AppsPreferences
import com.example.basestructure.model.remote.AppClient
import com.example.basestructure.model.remote.AppService
import io.reactivex.disposables.CompositeDisposable

class AppRepository(
    private val userDao: UserDao,
    private val remoteUser: AppService,
private val preferences: AppsPreferences) {

    suspend fun getUserApi(): List<User>{
        return remoteUser.users()
    }

    fun getUserDb(): LiveData<List<User>> {
        return userDao.getAllUser()
    }

    fun insertUser(user: User) {
        return userDao.insertUser(user)
    }

    fun savePort(sex: Set<String>) {
        preferences.setPort(sex)
    }
}