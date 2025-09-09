package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.sarang.torang.compose.type.LocalRestaurantGalleryInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantMenuInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantReviewInRestaurantDetailContainer
import com.sarang.torang.di.restaurant_detail_container_di.customRestaurantGalleryInRestaurantDetailContainer
import com.sarang.torang.di.restaurant_detail_container_di.customRestaurantMenuInRestaurantDetailContainer
import com.sarang.torang.di.restaurant_detail_container_di.customRestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.di.restaurant_detail_container_di.customRestaurantReviewInRestaurantDetailContainer
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TorangTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)){
                        CompositionLocalProvider(
                            LocalRestaurantOverviewInRestaurantDetailContainer provides customRestaurantOverviewInRestaurantDetailContainer(RootNavController()),
                            LocalRestaurantMenuInRestaurantDetailContainer provides customRestaurantMenuInRestaurantDetailContainer,
                            LocalRestaurantReviewInRestaurantDetailContainer provides customRestaurantReviewInRestaurantDetailContainer(RootNavController()),
                            LocalRestaurantGalleryInRestaurantDetailContainer provides customRestaurantGalleryInRestaurantDetailContainer,
                        ) {
                            RestaurantNavScreen(restaurantId = 234)
                        }
                    }
                }
            }
        }
    }
}
