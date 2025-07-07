package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.sarang.library.compose.type.LocalRestaurantOverviewInRestaurantDetailContainer
import com.sarang.library.compose.type.RestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.compose.restaurantdetail.RestaurantOverViewScreen
import com.sarang.torang.ui.theme.RestaurantDetailContailerTheme
import com.sarang.torang.compose.type.LocalRestaurantOverViewImageLoader
import com.sarang.torang.compose.type.LocalRestaurantOverviewRestaurantInfo
import com.sarang.torang.di.restaurant_overview_di.restaurantOverViewImageLoader
import com.sarang.torang.di.restaurant_overview_di.restaurantOverViewRestaurantInfo

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RestaurantDetailContailerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CompositionLocalProvider(LocalRestaurantOverviewInRestaurantDetailContainer provides customRestaurantOverviewInRestaurantDetailContainer) {
                        RestaurantNavScreen(restaurantId = 234)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    val customRestaurantOverviewInRestaurantDetailContainer : RestaurantOverviewInRestaurantDetailContainer = {
        CompositionLocalProvider(
            LocalRestaurantOverViewImageLoader provides restaurantOverViewImageLoader,
            LocalRestaurantOverviewRestaurantInfo provides restaurantOverViewRestaurantInfo,
        ) {
            RestaurantOverViewScreen(restaurantId = 234)
        }
    }
}
