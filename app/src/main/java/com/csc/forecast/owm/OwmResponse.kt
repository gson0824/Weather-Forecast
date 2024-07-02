package com.csc.forecast.owm

// One Call API 2.5
// https://openweathermap.org/api/one-call-api

data class Temp(
    val day: Double = 0.0,
    val min: Double = 0.0,
    val max: Double = 0.0
)

data class Weather(
    val id: Int = 0,
    val main: String = "",
    val description: String = "",
    val icon: String = ""
)

data class Daily(
    val dt: Int = 0,
    val temp: Temp = Temp(),
    val humidity: Int = 0,
    val weather: List<Weather> = listOf(),
)

data class Current(
    val dt: Int = 0,
    val temp: Double = 0.0,
    val weather: List<Weather> = listOf(),
)

data class OwmResponse(
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val timezone: String = "",
    val current: Current = Current(),
    val daily: List<Daily> = listOf(),
)
