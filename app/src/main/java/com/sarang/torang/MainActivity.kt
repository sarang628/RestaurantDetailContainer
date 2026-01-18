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
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sarang.torang.data.RestaurantWithFiveImages
import com.sarang.torang.di.restaurant_detail_container_di.provideRestaurantDetailContainer
import com.sarang.torang.repository.FindRepository
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var findRepository: FindRepository

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TorangTheme {
                TestContainer()
            }
        }
    }

    @Composable
    fun RestaurantDetailContainerTest(selectedRestaurant : Int){
        provideRestaurantDetailContainer().invoke(selectedRestaurant)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TestContainer(){
        val scaffoldState = rememberBottomSheetScaffoldState()
        val scope = rememberCoroutineScope()
        var restaurants : List<RestaurantWithFiveImages> by remember { mutableStateOf(listOf()) }
        LaunchedEffect(Unit) {
            findRepository.restaurants
                .stateIn(scope = scope,
                    started = SharingStarted.Eagerly,
                    initialValue = listOf()).collect {
                        restaurants = it
                }
        }
        var selectedRestaurant by remember { mutableIntStateOf(0) }


        val sheetContent = @Composable {
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
        }

        fun floatingButton(modifier : Modifier) = @Composable {
            FloatingActionButton(
                modifier = modifier,
                onClick = {
                    scope.launch {
                        findRepository.findFilter()
                        scaffoldState.bottomSheetState.expand()
                    }
                }) {
                Icon(Icons.AutoMirrored.Default.List, null)
            }
        }

        BottomSheetScaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            sheetPeekHeight = 0.dp,
            sheetContent = { sheetContent.invoke() },
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)){
                RestaurantDetailContainerTest(selectedRestaurant)

                floatingButton(modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 24.dp, end = 16.dp)).invoke()
            }
        }
    }
}