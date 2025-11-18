package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomAppBarState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sarang.torang.compose.type.LocalRestaurantGalleryInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantMenuInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantReviewInRestaurantDetailContainer
import com.sarang.torang.di.restaurant_detail_container_di.customRestaurantGalleryInRestaurantDetailContainer
import com.sarang.torang.di.restaurant_detail_container_di.customRestaurantMenuInRestaurantDetailContainer
import com.sarang.torang.di.restaurant_detail_container_di.customRestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.di.restaurant_detail_container_di.customRestaurantReviewInRestaurantDetailContainer
import com.sarang.torang.repository.FindRepository
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TorangTheme {
                Test()
            }
        }
    }

    @Inject
    lateinit var findRepository: FindRepository

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Test(){
        val scaffoldState = rememberBottomSheetScaffoldState()
        val scope = rememberCoroutineScope()
        val restaurants by findRepository.restaurants.collectAsStateWithLifecycle()
        var selectedRestaurant by remember { mutableStateOf(0) }
        BottomSheetScaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            sheetPeekHeight = 0.dp,
            sheetContent = {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(restaurants.reversed()){
                        TextButton({
                            selectedRestaurant = it.restaurant.restaurantId
                            scope.launch {
                                scaffoldState.bottomSheetState.partialExpand()
                            }
                        }) {
                            Text(it.restaurant.restaurantName)
                        }
                    }
                }
            },
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)){
                CompositionLocalProvider(
                    LocalRestaurantOverviewInRestaurantDetailContainer provides customRestaurantOverviewInRestaurantDetailContainer(RootNavController()),
                    LocalRestaurantMenuInRestaurantDetailContainer provides customRestaurantMenuInRestaurantDetailContainer,
                    LocalRestaurantReviewInRestaurantDetailContainer provides customRestaurantReviewInRestaurantDetailContainer(RootNavController()),
                    LocalRestaurantGalleryInRestaurantDetailContainer provides customRestaurantGalleryInRestaurantDetailContainer,
                ) {
                    RestaurantNavScreen(restaurantId = selectedRestaurant)
                }

                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 24.dp, end = 16.dp)
                    ,
                    onClick = {
                    scope.launch {
                        findRepository.findFilter()
                        scaffoldState.bottomSheetState.expand()
                    }
                }) {
                    Icon(Icons.AutoMirrored.Default.List, null)
                }
            }
        }
    }
}
