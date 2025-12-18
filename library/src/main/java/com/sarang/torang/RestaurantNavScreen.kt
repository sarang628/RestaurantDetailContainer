package com.sarang.torang

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.type.LocalRestaurantGalleryInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantMenuInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantOverviewInRestaurantDetailContainer
import com.sarang.torang.compose.type.LocalRestaurantReviewInRestaurantDetailContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RestaurantNavScreen(onBack           : () -> Unit,
                                restaurantName   : String,
                                restaurantId     : Int,
                                snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }) {
    val scrollBehavior    = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val navController     = rememberNavController()
    val pagerState        = rememberPagerState(0) { 4 }
    Scaffold(
        topBar       = { TopAppBar(navigationIcon = arrowBack(onBack),
                             title          = restaurantTitleText(restaurantName),
                             scrollBehavior = scrollBehavior) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content      = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues = paddingValues)) {
                RestaurantTopMenu(pagerState = pagerState)
                /*NavHost(navController = navController, startDestination = "overview") {
                    composable("overview") { LocalRestaurantOverviewInRestaurantDetailContainer.current.invoke(restaurantId) }
                    composable("menu") { LocalRestaurantMenuInRestaurantDetailContainer.current.invoke(restaurantId) }
                    composable("review") { LocalRestaurantReviewInRestaurantDetailContainer.current.invoke(restaurantId) }
                    composable("gallery") { LocalRestaurantGalleryInRestaurantDetailContainer.current.invoke(restaurantId) }
                }*/
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

@Composable
fun arrowBack(onBack: () -> Unit) : @Composable () -> Unit = {
    IconButton(onClick = onBack) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = ""
        )
    }
}

@Composable
fun restaurantTitleText(restaurantName: String): @Composable () -> Unit = {
    Text(text = restaurantName,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantNavScreen(
    viewmodel: RestaurantNavViewModel = hiltViewModel(),
    tag: String = "__RestaurantNavScreen",
    restaurantId: Int,
    progressTintColor: Color? = null,
    onImage: (Int) -> Unit = { { Log.w(tag, "onImage doesn't set") } },
    onBack: (() -> Unit) = { Log.w(tag, "onBack doesn't set") },
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    LaunchedEffect(restaurantId) { viewmodel.fetch(restaurantId) }
    RestaurantNavScreen(onBack            = onBack,
                        restaurantName    = viewmodel.restaurantName,
                        restaurantId      = restaurantId,
                        snackbarHostState = snackbarHostState)
}

@Preview
@Composable
fun PreviewRestaurantNavScreen() {
    RestaurantNavScreen(onBack = {}, "restaurantName", restaurantId = 234)
}