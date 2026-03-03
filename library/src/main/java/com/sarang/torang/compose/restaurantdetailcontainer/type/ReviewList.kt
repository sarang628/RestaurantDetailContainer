package com.sarang.torang.compose.restaurantdetailcontainer.type

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

typealias ReviewList = @Composable (Int) -> Unit

val LocalRestaurantReviewInRestaurantDetailContainer =
    compositionLocalOf<ReviewList> {
        @Composable {
            Text("no RestaurantReviewInRestaurantDetailContainer")
            Log.w("__RestaurantReviewInRestaurantDetailContainer", "no RestaurantReviewInRestaurantDetailContainer")
        }
    }

