package com.sarang.torang

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.library.RestaurantTopMenu
import com.sarang.library.compose.type.LocalRestaurantOverviewInRestaurantDetailContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantNavScreen(
    viewmodel: RestaurantNavViewModel = hiltViewModel(),
    tag: String = "__RestaurantNavScreen",
    restaurantId: Int,
    progressTintColor: Color? = null,
    onImage: (Int) -> Unit = { { Log.w(tag, "onImage doesn't set") } },
    onBack: (() -> Unit) = { Log.w(tag, "onBack doesn't set") },
) {
    LaunchedEffect(restaurantId) { viewmodel.fetch(restaurantId) }
    RestaurantNavScreen(onBack, viewmodel.restaurantName)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RestaurantNavScreen(onBack: () -> Unit, restaurantName: String) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val snackBarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { IconButton(onClick = onBack) { Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "") } },
                title = { Text(text = restaurantName, fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                scrollBehavior = scrollBehavior
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues = paddingValues)) {
            RestaurantTopMenu(navController)
            NavHost(navController = navController, startDestination = "overview") {
                composable("overview") {
                    LocalRestaurantOverviewInRestaurantDetailContainer.current.invoke()
                }
                composable("menu") {
                    //RestaurantMenuScreen(restaurantId = restaurantId, progressTintColor = progressTintColor)
                }
                composable("review") {
                    //LocalFeeds.current.invoke(restaurantId, Modifier.nestedScroll(scrollBehavior.nestedScrollConnection))
                }
                composable("gallery") {
                    //RestaurantGalleryScreen(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), restaurantId = restaurantId, onImage = { onImage.invoke(it) })
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewRestaurantNavScreen() {
    RestaurantNavScreen(onBack = {}, "restaurantName")
}