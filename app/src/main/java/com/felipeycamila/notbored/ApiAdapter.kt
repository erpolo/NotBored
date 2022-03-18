package com.felipeycamila.notbored

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiAdapter {

     private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://www.boredapi.com/api/activity")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService() = getRetrofit().create(ApiService::class.java)

}