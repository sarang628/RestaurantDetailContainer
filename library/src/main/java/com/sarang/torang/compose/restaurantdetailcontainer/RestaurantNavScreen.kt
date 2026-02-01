package com.sarang.torang.compose.restaurantdetailcontainer

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.restaurantdetailcontainer.type.LocalRestaurantGalleryInRestaurantDetailContainer
import com.sarang.torang.compose.restaurantdetailcontainer.type.LocalRestaurantMenuInRestaurantDetailContainer
import com.sarang.torang.compose.restaurantdetailcontainer.type.LocalRestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.compose.restaurantdetailcontainer.type.LocalRestaurantReviewInRestaurantDetailContainer
import com.sarang.torang.compose.restaurantdetailcontainer.type.RestaurantGalleryInRestaurantDetailContainer
import com.sarang.torang.compose.restaurantdetailcontainer.type.RestaurantMenuInRestaurantDetailContainer
import com.sarang.torang.compose.restaurantdetailcontainer.type.RestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.compose.restaurantdetailcontainer.type.RestaurantReviewInRestaurantDetailContainer

private val tag: String = "__RestaurantNavScreen"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RestaurantNavScreen(onBack           : () -> Unit,
                                restaurantName   : String,
                                restaurantId     : Int,
                                snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }) {
    val scrollBehavior    = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val pagerState        = rememberPagerState(0) { 4 }
    Scaffold(
        topBar       = { TopAppBar(navigationIcon = arrowBack(onBack),
                                   title          = restaurantTitleText(restaurantName),
                                   colors         = TopAppBarDefaults.topAppBarColors(scrolledContainerColor = MaterialTheme.colorScheme.background),
                                   scrollBehavior = scrollBehavior) },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        content      = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues = paddingValues)
                                      .nestedScroll(scrollBehavior.nestedScrollConnection)) {
                RestaurantTopMenu(pagerState = pagerState)
                HorizontalPager(state = pagerState,
                                beyondViewportPageCount = 3) {
                    when(it){
                        0 -> { LocalRestaurantOverviewInRestaurantDetailContainer.current.invoke(restaurantId) }
                        1 -> { LocalRestaurantMenuInRestaurantDetailContainer.current.invoke(restaurantId) }
                        2 -> { LocalRestaurantReviewInRestaurantDetailContainer.current.invoke(restaurantId) }
                        3 -> { LocalRestaurantGalleryInRestaurantDetailContainer.current.invoke(restaurantId) }
                    }
                }
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantNavScreenWithModules(viewmodel           : RestaurantNavViewModel    = hiltViewModel(),
                                   overView  : RestaurantOverviewInRestaurantDetailContainer,
                                   menu      : RestaurantMenuInRestaurantDetailContainer,
                                   review              : RestaurantReviewInRestaurantDetailContainer,
                                   gallery             : RestaurantGalleryInRestaurantDetailContainer,
                                   restaurantId        : Int                       = 0,
                                   onBack              : () -> Unit                = { Log.w(tag, "onBack doesn't set") },
                                   snackBarHostState   : SnackbarHostState         = remember { SnackbarHostState() }) {
    LaunchedEffect(restaurantId) {
        viewmodel.fetch(restaurantId)
    }
    CompositionLocalProvider(LocalRestaurantOverviewInRestaurantDetailContainer provides overView,
        LocalRestaurantMenuInRestaurantDetailContainer provides menu,
        LocalRestaurantReviewInRestaurantDetailContainer provides review,
        LocalRestaurantGalleryInRestaurantDetailContainer provides gallery) {
        RestaurantNavScreen(onBack            = onBack,
            restaurantName    = viewmodel.restaurantName,
            restaurantId      = restaurantId,
            snackBarHostState = snackBarHostState)
    }
}

@Preview
@Composable
fun PreviewRestaurantNavScreen() {
    RestaurantNavScreen(onBack          = {},
                        restaurantName  = "restaurantName",
                        restaurantId    = 234)
}