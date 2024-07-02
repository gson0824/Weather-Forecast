package com.csc.forecast.owm

import android.util.Log
import com.csc.forecast.BuildConfig
import com.csc.forecast.ForecastApp
import com.csc.forecast.ForecastApp.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class OwmRepository {

    companion object {
        val owmApi: OwmApi by lazy {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")  // Ensure this matches your successful curl request
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return@lazy retrofit.create(OwmApi::class.java)
        }
    }

    fun fetchData(onFailure: (Throwable) -> Unit, onSuccess: (OwmResponse) -> Unit) {
        val apiKey = BuildConfig.API_KEY
        val lat = ForecastApp.LAT
        val lon = ForecastApp.LON
        val exclude = ForecastApp.EXCLUDE
        val units = when (Locale.getDefault().language) {
            "en" -> "imperial"
            else -> "metric"
        }
        val lang = when (Locale.getDefault().language) {
            "de", "en", "fr" -> Locale.getDefault().language
            else -> "en"
        }

        // Log the parameters
        Log.d("OwmRepository", "Using API Key: $apiKey")
        Log.d("OwmRepository", "Latitude: $lat, Longitude: $lon")
        Log.d("OwmRepository", "Exclude: $exclude, Units: $units, Lang: $lang")

        val request: Call<OwmResponse> = owmApi.fetchForecastsOneCall(
            lat,
            lon,
            apiKey,
            exclude,
            units,
            lang
        )

        request.enqueue(object : Callback<OwmResponse> {
            override fun onFailure(call: Call<OwmResponse>, t: Throwable) {
                Log.e("OwmRepository", "API call failed", t)
                onFailure(t)
            }

            override fun onResponse(
                call: Call<OwmResponse>,
                response: Response<OwmResponse>
            ) {
                if (response.isSuccessful) {
                    val data: OwmResponse? = response.body()
                    if (data != null) {
                        Log.d("OwmRepository", "API call succeeded: $data")
                        onSuccess(data)
                    } else {
                        Log.e("OwmRepository", "API call returned null data")
                        onFailure(Throwable("API call returned null data"))
                    }
                } else {
                    Log.e("OwmRepository", "API call failed with response code: ${response.code()}")
                    onFailure(Throwable("API call failed with response code: ${response.code()}"))
                }
            }
        })
    }
}