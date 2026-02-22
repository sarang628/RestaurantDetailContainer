package com.sarang.torang

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TabWithLazyColumn() {
    val tabs = listOf("Tab1", "Tab2", "Tab3")
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var selectedTabIndex by remember { mutableStateOf(0) }

    // LazyColumn 스크롤 위치를 관찰해서 탭 선택 업데이트
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { firstVisibleIndex ->
                // 각 탭마다 10개씩 아이템을 배치했다고 가정
                val newTabIndex = firstVisibleIndex / 10
                if (newTabIndex != selectedTabIndex && newTabIndex < tabs.size) {
                    selectedTabIndex = newTabIndex
                }
            }
    }

    Column {

        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        coroutineScope.launch {
                            listState.animateScrollToItem(index * 10)
                        }
                    },
                    text = { Text(title) }
                )
            }
        }

        LazyColumn(
            state = listState
        ) {
            items(50) { index ->
                Text(
                    text = "Item $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}