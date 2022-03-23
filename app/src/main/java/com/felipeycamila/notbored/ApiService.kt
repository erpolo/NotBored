package com.felipeycamila.notbored

import com.felipeycamila.notbored.ActivityModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
/*
    @GET("activity/")
    suspend fun getRandomActivity() : Response<ActivityModel>

    @GET("activity")
    suspend fun getRandomActivity(@Query("participants") participants : String) : Response<ActivityModel>

    @GET("activity")
    suspend fun getRandomActivityPrice(@Query("price") price : String) : Response<ActivityModel>

    @GET("activity")
    suspend fun getRandomActivity(@Query("participants") participants : String, @Query("price") price : String) : Response<ActivityModel>

    @GET("activity")
    suspend fun getActivity(@Query("type") type : String) : Response<ActivityModel>

    @GET("activity")
    suspend fun getActivityPrice(@Query("type") type : String, @Query("price") price: String) : Response<ActivityModel>

    @GET("activity")
    suspend fun getActivity(@Query("type") type : String, @Query("participants") participants: String) : Response<ActivityModel>
*/
    @GET("activity")
    suspend fun getActivity(@Query("type") type : String?=null, @Query("participants") participants: String?=null,
                            @Query("minprice") minprice : String?=null, @Query("maxprice") maxprice : String?=null) : Response<ActivityModel>

}