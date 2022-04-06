package com.example.basestructure.model.remote

import com.example.basestructure.model.entities.User
import io.reactivex.Observable
import retrofit2.http.GET

interface AppService {
   @GET("thonn96/run-app-in-background/main/users.json")
   suspend fun users(): List<User>
}