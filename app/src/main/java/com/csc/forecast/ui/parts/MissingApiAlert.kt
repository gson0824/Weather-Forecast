package com.csc.forecast.ui.parts

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csc.forecast.R
import com.csc.forecast.ui.theme.ForecastTheme

@Composable
fun MissingApiAlert(
    showNoApiAlert: MutableState<Boolean>,
    onOK: () -> Unit,
) {
    if (showNoApiAlert.value) {
        AlertDialog(
            title = { Text(stringResource(R.string.error)) },
            text = {
                Text(
                    stringResource(id = R.string.no_api_key_alert),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontWeight = FontWeight.Medium,
                    fontSize = 17.sp,
                )
            },
            onDismissRequest = { },
            confirmButton = {
                Button(onClick = onOK) {
                    Text(stringResource(R.string.ok))
                }
            },
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MissingApiAlertPreview() {
    ForecastTheme {
        val state = remember { mutableStateOf(true) }
        MissingApiAlert(
            showNoApiAlert = state,
            onOK = {},
        )
    }
}