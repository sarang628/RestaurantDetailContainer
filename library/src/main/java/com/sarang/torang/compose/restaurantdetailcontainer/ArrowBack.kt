package com.sarang.torang.compose.restaurantdetailcontainer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun arrowBack(onBack: () -> Unit) : @Composable () -> Unit = {
    IconButton(onClick = onBack) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = ""
        )
    }
}