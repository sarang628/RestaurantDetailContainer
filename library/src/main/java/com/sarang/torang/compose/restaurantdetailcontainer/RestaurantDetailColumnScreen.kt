package com.sarang.torang.compose.restaurantdetailcontainer

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.restaurantdetailcontainer.type.RestaurantInfo
import kotlinx.coroutines.launch

private val tag: String = "__RestaurantNavScreen"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RestaurantDetailColumnScreen(modifier               : Modifier          = Modifier,
                                         onBack                 : () -> Unit        = {},
                                         restaurantName         : String            = "",
                                         restaurantId           : Int               = 0,
                                         snackBarHostState      : SnackbarHostState = remember { SnackbarHostState() },
                                         reviewListContent      : LazyListScope.() -> Unit = {},
                                         galleryContent         : LazyListScope.() -> Unit = {},
                                         restaurantInfo         : RestaurantInfo    = {},
                                         menuListContent        : LazyListScope.() -> Unit = {},
                                         menuItemCount          : Int = 0,
                                         reviewItemCount        : Int = 0,
                                         galleryItemCount       : Int = 0,
                                         ) {
    val scrollBehavior    = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val state: LazyListState = rememberLazyListState()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val coroutine = rememberCoroutineScope()
    val overview = 0
    val menu = 1
    var review by remember { mutableIntStateOf(menu + menuItemCount+1) }
    var gallery by remember { mutableIntStateOf(review + reviewItemCount + 1) }


    LaunchedEffect(key1 = reviewItemCount,menuItemCount, galleryItemCount) {
        snapshotFlow { state.layoutInfo.visibleItemsInfo.first().index }
            .collect {
                review = menu + menuItemCount + 1
                gallery = review + reviewItemCount + 1
                Log.d(tag, "$reviewItemCount")
                if(it == overview){
                    selectedTabIndex = 0
                }else if(it in menu until review){
                    selectedTabIndex = 1
                }else if(it in review until gallery){
                    selectedTabIndex = 2
                }else{
                    selectedTabIndex = 3
                }

            }
    }

    Scaffold(modifier = modifier,
        topBar       = {
            Column {
                TopAppBar(navigationIcon = arrowBack(onBack),
                          title          = restaurantTitleText(restaurantName),
                          colors         = TopAppBarDefaults.topAppBarColors(scrolledContainerColor = MaterialTheme.colorScheme.background),
                          scrollBehavior = scrollBehavior)
                RestaurantTopMenu(selectedTabIndex = selectedTabIndex,
                                  onSelectTab = {
                                      coroutine.launch {
                                          when(it){
                                              0 -> { state.animateScrollToItem(0) }
                                              1 -> { state.animateScrollToItem(1) }
                                              2 -> { state.animateScrollToItem(review) }
                                              3 -> { state.animateScrollToItem(gallery ) }
                                          }
                                      }
                                  })
            }
                       },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        content      = { paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues = paddingValues),
                       state = state) {

                item { restaurantInfo.invoke(restaurantId) }

                menuListContent.invoke(this)

                reviewListContent.invoke(this)

                galleryContent.invoke(this)

            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailColumnScreenWithModules(viewmodel               : RestaurantNavViewModel    = hiltViewModel(),
                                            reviewListContent      : LazyListScope.() -> Unit = {},
                                            restaurantOverviewInfo : RestaurantInfo             = { },
                                            restaurantId            : Int                       = 0,
                                            onBack                  : () -> Unit                = { Log.w(tag, "onBack doesn't set") },
                                            snackBarHostState       : SnackbarHostState         = remember { SnackbarHostState() },
                                            galleryContent         : LazyListScope.() -> Unit = {},
                                            menuListContent        : LazyListScope.() -> Unit = {},
                                            menuItemCount          : Int = 0,
                                            reviewItemCount        : Int = 0,
                                            galleryItemCount       : Int = 0) {
    LaunchedEffect(restaurantId) {
        viewmodel.fetch(restaurantId)
    }

    RestaurantDetailColumnScreen(onBack             = onBack,
                                 restaurantName     = viewmodel.restaurantName,
                                 restaurantId       = restaurantId,
                                 snackBarHostState  = snackBarHostState,
                                 menuListContent    = menuListContent,
                                 reviewListContent  = reviewListContent,
                                 galleryContent     = galleryContent,
                                 restaurantInfo     = restaurantOverviewInfo,
                                 menuItemCount      = menuItemCount,
                                 reviewItemCount    = reviewItemCount,
                                 galleryItemCount   = galleryItemCount)

}

@Preview
@Composable
fun PreviewRestaurantDetailColumnScreen() {
    RestaurantDetailColumnScreen(onBack          = {}, //Preview
                        restaurantName  = "restaurantName",
                        restaurantId    = 234)
}