package com.sarang.torang.compose.restaurantdetailcontainer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun restaurantTitleText(restaurantName: String): @Composable () -> Unit = {
    Text(text = restaurantName,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis)
}
