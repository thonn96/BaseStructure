package com.example.basestructure.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basestructure.model.daos.UserDao
import com.example.basestructure.model.entities.User
import com.example.basestructure.model.repositories.AppDatabase
import com.example.basestructure.model.repositories.AppRepository
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import kotlin.coroutines.CoroutineContext

class DashboardViewModel(private val appRepository: AppRepository) : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun getALlUser(): LiveData<List<User>>{
        return appRepository.getUserDb()
    }

    fun getUserFromApi(coroutineScope: CoroutineScope = this) = coroutineScope.launch {
        withContext(Dispatchers.IO){
            makeCallUserApi().forEach {
                appRepository.insertUser(it)
            }
        }
    }

    fun savePreference(sex: Set<String>) {
        appRepository.savePort(sex)
    }

    private suspend fun makeCallUserApi(): List<User> = withContext(Dispatchers.IO) {
        return@withContext appRepository.getUserApi()
    }
}
class DashboardViewModelFactory(val appRepository: AppRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DashboardViewModel(appRepository) as T
    }
}