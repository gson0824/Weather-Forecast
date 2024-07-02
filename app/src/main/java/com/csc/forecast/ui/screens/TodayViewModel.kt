package com.csc.forecast.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import com.csc.forecast.ForecastApp
import com.csc.forecast.owm.Daily
import com.csc.forecast.owm.OwmRepository
import com.csc.forecast.owm.OwmResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TodayViewModel(private val owmRepo: OwmRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(TodayUiState())
    val uiState: StateFlow<TodayUiState> = _uiState

    private fun updateState(owmResponse: OwmResponse) {
        _uiState.value = _uiState.value.copy(
            icon = owmResponse.current.weather[0].icon,
            temp = owmResponse.current.temp,
            description = owmResponse.current.weather[0].description,
            dateTime = owmResponse.current.dt,
            timezone = owmResponse.timezone,
            dailies = owmResponse.daily
        )
        Log.d("TodayViewModel", "State updated: ${_uiState.value}")
    }

    fun fetchData(fromFile: Boolean) {
        if (fromFile) {
            Log.d("TodayViewModel", "Fetching data from file")
            val data = ForecastApp.owmSample
            updateState(data)
        } else {
            Log.d("TodayViewModel", "Fetching data from API")
            owmRepo.fetchData(
                onFailure = { error ->
                    Log.e("TodayViewModel", "Failed to fetch data from API", error as Throwable?)
                }
            ) { owmResponse ->
                Log.d("TodayViewModel", "Data fetched from API: $owmResponse")
                updateState(owmResponse)
            }
        }
    }
}

data class TodayUiState(
    val icon: String = "",
    val temp: Double = 0.0,
    val description: String = "",
    val dateTime: Int = 0,
    val timezone: String = "",
    val dailies: List<Daily> = listOf(),
)
