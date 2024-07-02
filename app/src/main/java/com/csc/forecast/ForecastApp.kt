package com.csc.forecast

import android.app.Application
import com.csc.forecast.owm.OwmResponse
import com.google.gson.Gson

class ForecastApp : Application() {

    companion object {

        private lateinit var instance: ForecastApp

        lateinit var owmSample: OwmResponse

        const val LAT = 43.236
        const val LON = -77.6933
        const val EXCLUDE = "minutely,hourly,alerts"
        const val BASE_URL = "https://api.openweathermap.org"
        const val IMAGE_URL = "https://openweathermap.org/img/wn/"
        const val IMAGE_EXTENSION = "@2x.png"
    }

    override fun onCreate() {
        instance = this
        super.onCreate()

        val gson = Gson()
        val jsonString =
            assets.open("OwmTest.json").bufferedReader().use {
                it.readText()
            }
        owmSample = gson.fromJson(jsonString, OwmResponse::class.java)
        print(owmSample)
    }
}