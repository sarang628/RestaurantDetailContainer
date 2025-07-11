package com.sarang.torang.compose.type

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

typealias RestaurantMenuInRestaurantDetailContainer = @Composable () -> Unit

val LocalRestaurantMenuInRestaurantDetailContainer =
    compositionLocalOf<RestaurantMenuInRestaurantDetailContainer> {
        @Composable {
            Text("no RestaurantMenuInRestaurantDetailContainer")
            Log.w("__RestaurantMenuInRestaurantDetailContainer", "no RestaurantMenuInRestaurantDetailContainer")
        }
    }

