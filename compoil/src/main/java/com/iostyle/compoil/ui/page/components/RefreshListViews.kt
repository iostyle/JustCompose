package com.iostyle.compoil.ui.page.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.iostyle.compoil.data.Records
import com.iostyle.compoil.ui.theme.Text
import com.iostyle.compoil.ui.theme.color

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefreshIndicatorView(
    modifier: Modifier = Modifier,
    dataList: List<Records>,
    isRefreshing: Boolean,
    refresh: () -> Unit,
    delete: (item: Records) -> Unit
) {
    // init
    LaunchedEffect(Unit) {
        refresh()
    }

    val refreshState = rememberPullRefreshState(isRefreshing, onRefresh = refresh)
    val dataListLazySate = rememberLazyListState()

    Box(modifier.pullRefresh(refreshState)) {
        LazyColumn(modifier.fillMaxSize(), state = dataListLazySate) {
            items(dataList, key = { it.timestamp }) { item ->
                OilItemView(item, delete)
            }
        }

        PullRefreshIndicator(isRefreshing, refreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun OilItemView(item: Records, delete: (Records) -> Unit) {
    SwipeToDismissItem({
        delete(item)
    }) {
        Text(
            text = "当前里程:${item.currentMileage}\n本次加油量:${item.oilInjection}", color = Text.color,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surface
                )
        )
    }
}
