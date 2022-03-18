package com.felipeycamila.notbored

import com.felipeycamila.notbored.ActivityModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    //consultar si es opcional el numero de participantes.
    @GET()
    fun getRandomActivity(@Url participants : String) : Response<ActivityModel>

    @GET()
    fun getActivityType(@Url type : String, participants: String) : Response<ActivityModel>
}