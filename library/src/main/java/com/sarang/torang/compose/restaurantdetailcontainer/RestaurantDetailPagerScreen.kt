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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.restaurantdetailcontainer.type.RestaurantGalleryInRestaurantDetailContainer
import com.sarang.torang.compose.restaurantdetailcontainer.type.RestaurantMenuInRestaurantDetailContainer
import com.sarang.torang.compose.restaurantdetailcontainer.type.RestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.compose.restaurantdetailcontainer.type.RestaurantReviewInRestaurantDetailContainer

private val tag: String = "__RestaurantNavScreen"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RestaurantDetailPagerScreen(onBack            : () -> Unit                                      = {},
                                        restaurantName    : String                                          = "",
                                        restaurantId      : Int                                             = 0,
                                        snackBarHostState : SnackbarHostState                               = remember { SnackbarHostState() },
                                        overView          : RestaurantOverviewInRestaurantDetailContainer   = {},
                                        menu              : RestaurantMenuInRestaurantDetailContainer       = {},
                                        review            : RestaurantReviewInRestaurantDetailContainer     = {},
                                        gallery           : RestaurantGalleryInRestaurantDetailContainer    = {}) {
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
                        0 -> { overView.invoke(restaurantId) }
                        1 -> { menu.invoke(restaurantId) }
                        2 -> { review.invoke(restaurantId) }
                        3 -> { gallery.invoke(restaurantId) }
                    }
                }
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailPagerWithModules(viewmodel           : RestaurantNavViewModel    = hiltViewModel(),
                                     overView            : RestaurantOverviewInRestaurantDetailContainer,
                                     menu                : RestaurantMenuInRestaurantDetailContainer,
                                     review              : RestaurantReviewInRestaurantDetailContainer,
                                     gallery             : RestaurantGalleryInRestaurantDetailContainer,
                                     restaurantId        : Int                       = 0,
                                     onBack              : () -> Unit                = { Log.w(tag, "onBack doesn't set") },
                                     snackBarHostState   : SnackbarHostState         = remember { SnackbarHostState() }) {
    LaunchedEffect(restaurantId) {
        viewmodel.fetch(restaurantId)
    }

    RestaurantDetailPagerScreen(onBack            = onBack,
                                restaurantName    = viewmodel.restaurantName,
                                restaurantId      = restaurantId,
                                snackBarHostState = snackBarHostState,
                                menu              = menu,
                                review            = review,
                                gallery           = gallery,
                                overView          = overView)
}

@Preview
@Composable
fun PreviewRestaurantDetailPagerScreen() {
    RestaurantDetailPagerScreen(onBack          = {}, //Preview
                        restaurantName  = "restaurantName",
                        restaurantId    = 234)
}