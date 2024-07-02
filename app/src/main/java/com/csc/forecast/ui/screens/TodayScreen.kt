package com.csc.forecast.ui.screens

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.csc.forecast.ForecastApp.Companion.IMAGE_EXTENSION
import com.csc.forecast.ForecastApp.Companion.IMAGE_URL
import com.csc.forecast.R
import com.csc.forecast.owm.Daily
import com.csc.forecast.ui.theme.ForecastTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TodayScreen(
    todayUiState: TodayUiState,
    modifier: Modifier = Modifier,
    refresh: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .fillMaxSize(),
        ) {
            TodayHeader(state = todayUiState)
            Divider(
                color = Color.Gray, modifier = Modifier
                    .padding(horizontal = 48.dp)
                    .fillMaxWidth()
                    .width(1.dp)
            )
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 8.dp)
            ) {
                items(
                    items = todayUiState.dailies,
                    itemContent = { daily ->
                        DailyItem(daily = daily)
                    }
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .padding(32.dp),
            onClick = refresh
        ) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = stringResource(id = R.string.refresh)
            )
        }
    }
}

@Composable
fun TodayHeader(state: TodayUiState) {

    val date = Date(state.dateTime.toLong() * 1000L)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(12.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(24.dp)
                .size(128.dp),
            model = IMAGE_URL + state.icon + IMAGE_EXTENSION,
            placeholder = painterResource(id = R.drawable.shutter_reverse),
            contentDescription = null,
        )
        Column {
            Text(
                text = SimpleDateFormat("EEEE", Locale.getDefault()).format(date),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
            )
            Text(
                text = state.timezone,
            )
            Text(
                text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(date),
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 21.sp
                )
            )
            Text(
                text = state.temp.toString() + "º",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = state.description,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Composable
fun DailyItem(
    daily: Daily,
) {
    val date = Date(daily.dt.toLong() * 1000L)

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(76.dp),
                model = IMAGE_URL + daily.weather[0].icon + IMAGE_EXTENSION,
                placeholder = painterResource(id = R.drawable.shutter_reverse),
                contentDescription = null,
            )

            Column {
                Text(
                    text = when {
                        DateUtils.isToday(date.time) -> stringResource(id = R.string.today)
                        DateUtils.isToday(date.time - DateUtils.DAY_IN_MILLIS) -> stringResource(id = R.string.tomorrow)
                        else -> {
                            SimpleDateFormat("EEEE", Locale.getDefault()).format(date)
                        }
                    },
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 21.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )

                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = daily.temp.day.toString() + "º",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                    )

                    Row(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(id = R.string.humidity) + ": ",
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = daily.humidity.toString() + "%",
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                Text(
                    text = daily.weather[0].description,
                    style = TextStyle(
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodayScreenPreview() {
    ForecastTheme {
        TodayScreen(
            todayUiState = TodayUiState(),
            refresh = {},
        )
    }
}