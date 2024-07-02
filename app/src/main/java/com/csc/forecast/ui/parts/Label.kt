package com.csc.forecast.ui.parts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Label(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = text,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold
    )
}
