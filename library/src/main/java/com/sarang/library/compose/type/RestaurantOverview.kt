package com.sarang.library.compose.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

typealias RestaurantOverviewInRestaurantDetailContainer = @Composable () -> Unit

val LocalRestaurantOverviewInRestaurantDetailContainer =
    compositionLocalOf<RestaurantOverviewInRestaurantDetailContainer> {
        @Composable {

        }
    }

