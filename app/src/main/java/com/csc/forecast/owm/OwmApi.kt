package com.csc.forecast.owm

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OwmApi {
    @GET("data/3.0/onecall")
    fun fetchForecastsOneCall(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
        @Query("exclude") exclude: String,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): Call<OwmResponse>
}