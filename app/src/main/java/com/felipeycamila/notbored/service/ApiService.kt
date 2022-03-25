package com.felipeycamila.notbored.service

import com.felipeycamila.notbored.model.ActivityModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /**
     *It is responsible for making the GET request to the API, when a NULL parameter is passed to @query, it ignores null parameter.
     */
    @GET("activity")
    suspend fun getActivity(
        @Query("type") type: String? = null, @Query("participants") participants: String? = null,
        @Query("minprice") minprice: String? = null, @Query("maxprice") maxprice: String? = null
    ): Response<ActivityModel>

}