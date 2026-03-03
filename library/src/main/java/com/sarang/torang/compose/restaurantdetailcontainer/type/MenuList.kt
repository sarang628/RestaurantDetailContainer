package com.sarang.torang.compose.restaurantdetailcontainer.type

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

typealias MenuList = @Composable (Int) -> Unit

val LocalRestaurantMenuInRestaurantDetailContainer =
    compositionLocalOf<MenuList> {
        @Composable {
            Text("no RestaurantMenuInRestaurantDetailContainer")
            Log.w("__RestaurantMenuInRestaurantDetailContainer", "no RestaurantMenuInRestaurantDetailContainer")
        }
    }

