package com.felipeycamila.notbored

import com.felipeycamila.notbored.ActivityModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET("activity")
    suspend fun getActivity(
        @Query("type") type: String? = null, @Query("participants") participants: String? = null,
        @Query("minprice") minprice: String? = null, @Query("maxprice") maxprice: String? = null
    ): Response<ActivityModel>

}