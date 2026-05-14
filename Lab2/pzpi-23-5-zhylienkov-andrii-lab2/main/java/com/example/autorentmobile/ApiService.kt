package com.example.autorentmobile

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("api/mobileapi/cars")
    fun getCars(): Call<List<Car>>
}