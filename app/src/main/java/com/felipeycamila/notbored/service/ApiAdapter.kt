package com.felipeycamila.notbored.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiAdapter {

     private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.boredapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService() = getRetrofit().create(ApiService::class.java)
}