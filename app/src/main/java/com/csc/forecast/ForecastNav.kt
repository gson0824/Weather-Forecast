package com.csc.forecast

import com.csc.forecast.util.PreferenceUtil
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.csc.forecast.ui.parts.SettingsDialog
import com.csc.forecast.ui.screens.InfoScreen
import com.csc.forecast.ui.screens.TodayScreen
import com.csc.forecast.ui.screens.TodayUiState
import com.csc.forecast.ui.screens.TodayViewModel

object Route {
    const val TODAY = "Today"
    const val INFO = "Info"
}

data class Destination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val textId: Int,
)

val DESTINATIONS = listOf(
    Destination(
        route = Route.TODAY,
        selectedIcon = Icons.Default.AccountBox,
        unselectedIcon = Icons.Default.AccountBox,
        textId = R.string.title_today
    ),
    Destination(
        route = Route.INFO,
        selectedIcon = Icons.Default.Info,
        unselectedIcon = Icons.Default.Info,
        textId = R.string.title_info
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastNav(
    todayUiState: TodayUiState,
    todayViewModel: TodayViewModel,
    context: Context,
    readFromFile: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    refresh: () -> Unit,
) {
    val destination: MutableState<String> = remember { mutableStateOf(Route.TODAY) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    if (showSettingsDialog) {
        SettingsDialog(
            readFromFile = readFromFile,
            onDone = {
                PreferenceUtil.saveReadFromFile(context, readFromFile.value)
                todayViewModel.fetchData(fromFile = readFromFile.value)
                showSettingsDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = when (destination.value) {
                        Route.TODAY -> stringResource(id = R.string.title_today)
                        Route.INFO -> stringResource(id = R.string.title_info)
                        else -> ""
                    })
                },
                actions = {
                    IconButton(onClick = {
                        showSettingsDialog = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(id = R.string.title_settings)
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.fillMaxWidth()
            ) {
                DESTINATIONS.forEach { dest ->
                    NavigationBarItem(
                        label = { Text(text = stringResource(id = dest.textId)) },
                        selected = destination.value == dest.route,
                        onClick = { destination.value = dest.route },
                        icon = {
                            Icon(
                                imageVector = dest.selectedIcon,
                                contentDescription = stringResource(id = dest.textId)
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            when (destination.value) {
                Route.TODAY -> {
                    TodayScreen(
                        todayUiState = todayUiState,
                        modifier = Modifier.fillMaxSize(),
                        refresh = refresh,
                    )
                }
                Route.INFO -> {
                    InfoScreen(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}