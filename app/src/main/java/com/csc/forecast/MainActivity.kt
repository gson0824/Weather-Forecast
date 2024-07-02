@file:Suppress("UNCHECKED_CAST")

package com.csc.forecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.csc.forecast.owm.OwmRepository
import com.csc.forecast.ui.parts.MissingApiAlert
import com.csc.forecast.ui.screens.TodayUiState
import com.csc.forecast.ui.screens.TodayViewModel
import com.csc.forecast.ui.theme.ForecastTheme

class ForecastViewModelFactory(private val repository: OwmRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodayViewModel(owmRepo = repository) as T
    }
}

class MainActivity : ComponentActivity() {

    private val missingApiAlert = mutableStateOf(BuildConfig.API_KEY.isEmpty())

    private val owmRepository = OwmRepository()

    private val viewModelFactory = ForecastViewModelFactory(owmRepository)
    private val todayViewModel: TodayViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val readFromFile = remember { mutableStateOf(true) }
            todayViewModel.fetchData(fromFile = readFromFile.value)

            ForecastTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ForecastNav(
                        todayUiState = todayViewModel.uiState.collectAsState().value,
                        todayViewModel = todayViewModel,
                        context = this,
                        readFromFile = readFromFile,
                        refresh = {
                            todayViewModel.fetchData(fromFile = readFromFile.value)
                        }
                    )
                    MissingApiAlert(showNoApiAlert = missingApiAlert) {
                        missingApiAlert.value = false
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastAppPreview() {
    val readFromFile = remember { mutableStateOf(false) }
    ForecastTheme {
        ForecastNav(
            todayUiState = TodayUiState(),
            todayViewModel = TodayViewModel(OwmRepository()),
            context = LocalContext.current,
            readFromFile = readFromFile,
            refresh = {}
        )
    }
}
