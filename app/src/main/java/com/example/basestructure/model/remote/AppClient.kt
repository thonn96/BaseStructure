package com.example.basestructure.model.remote

import com.example.basestructure.model.entities.StatusType
import com.example.basestructure.model.entities.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat


object AppClient {
    const val MainServer = "https://raw.githubusercontent.com/"
  private val retrofitClient: Retrofit.Builder by lazy {

      val levelType= HttpLoggingInterceptor.Level.BODY
      val logging = HttpLoggingInterceptor()
      logging.setLevel(levelType)

      val okhttpClient = OkHttpClient.Builder()
      okhttpClient.addInterceptor(logging)
      val gson: Gson = GsonBuilder()
          .registerTypeAdapter(StatusType::class.java, UserConvertFactory())
          .enableComplexMapKeySerialization()
          .serializeNulls()
          .setDateFormat(DateFormat.LONG) //                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
          .setPrettyPrinting()
          .setVersion(1.0)
          .create()
      Retrofit.Builder()
          .baseUrl(MainServer)
          .client(okhttpClient.build())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create(gson))
  }

    val apiInterface: AppService by lazy {
        retrofitClient
            .build()
            .create(AppService::class.java)
    }
}