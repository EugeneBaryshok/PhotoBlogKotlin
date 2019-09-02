package com.example.photokotlin.data.model.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAdapter {

    private var retrofit: Retrofit? = null
    private var gson: Gson? = null
    private val BASE_URL = "https://jsonplaceholder.typicode.com"

    val instance: Retrofit?
        @Synchronized get() {

            if (retrofit == null) {
                if (gson == null) {
                    gson = GsonBuilder().setLenient().create()
                }

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson!!))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

            }

            return retrofit
        }


}