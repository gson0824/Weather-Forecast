package com.csc.forecast.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csc.forecast.BuildConfig
import com.csc.forecast.R
import com.csc.forecast.ui.theme.ForecastTheme

@Composable
fun InfoScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(8f)
                .padding(vertical = 108.dp)
        ) {
            Image(
                painterResource(id = R.mipmap.ic_launcher_foreground),
                contentDescription = null,
                Modifier
                    .clip(RoundedCornerShape(21.dp))
                    .background(Color.DarkGray)
                    .size(98.dp),
            )

            Column(
                modifier = Modifier
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontFamily = FontFamily.Cursive,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 56.sp,
                    )
                )
                Text(
                    text = BuildConfig.VERSION_NAME,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 21.sp,
                    )
                )
                Text(
                    text = BuildConfig.BUILD_TIME.toString(),
                    modifier = modifier,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Light,
                        fontSize = 11.sp,
                    )
                )
            }
        }

        Row(
            Modifier.weight(1f),
        ) {
            Text(
                text = stringResource(id = R.string.copyright),
                modifier = modifier,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoScreenPreview() {
    ForecastTheme {
        InfoScreen()
    }
}