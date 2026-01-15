package com.sarang.torang.compose.restaurantdetailcontainer.type

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

typealias RestaurantGalleryInRestaurantDetailContainer = @Composable (Int) -> Unit

val LocalRestaurantGalleryInRestaurantDetailContainer =
    compositionLocalOf<RestaurantMenuInRestaurantDetailContainer> {
        @Composable {
            Text("no RestaurantGalleryInRestaurantDetailContainer")
            Log.w("__RestaurantGalleryInRestaurantDetailContainer", "no RestaurantGalleryInRestaurantDetailContainer")
        }
    }

