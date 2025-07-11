package com.sarang.torang.compose.type

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

typealias RestaurantReviewInRestaurantDetailContainer = @Composable () -> Unit

val LocalRestaurantReviewInRestaurantDetailContainer =
    compositionLocalOf<RestaurantReviewInRestaurantDetailContainer> {
        @Composable {
            Text("no RestaurantReviewInRestaurantDetailContainer")
            Log.w("__RestaurantReviewInRestaurantDetailContainer", "no RestaurantReviewInRestaurantDetailContainer")
        }
    }

