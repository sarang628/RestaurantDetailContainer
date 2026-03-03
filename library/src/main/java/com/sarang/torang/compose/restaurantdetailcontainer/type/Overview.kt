package com.sarang.torang.compose.restaurantdetailcontainer.type

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

typealias RestaurantOverviewInRestaurantDetailContainer = @Composable (Int) -> Unit

val LocalRestaurantOverviewInRestaurantDetailContainer =
    compositionLocalOf<RestaurantOverviewInRestaurantDetailContainer> {
        @Composable {
            Text("no RestaurantOverviewInRestaurantDetailContainer")
            Log.w("__RestaurantOverviewInRestaurantDetailContainer", "no RestaurantOverviewInRestaurantDetailContainer")
        }
    }

