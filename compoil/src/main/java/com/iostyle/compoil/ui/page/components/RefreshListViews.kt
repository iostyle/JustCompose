package com.iostyle.compoil.ui.page.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.iostyle.compoil.bean.Records
import com.iostyle.compoil.ui.page.PageActions
import com.iostyle.compoil.ui.page.PageState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefreshIndicatorView(
    modifier: Modifier = Modifier,
    state: PageState,
    actions: PageActions
) {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        actions.onRefresh()
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)

    Box(modifier.pullRefresh(refreshState)) {
        LazyColumn(modifier.fillMaxSize()) {
            items(state.pageItems.size) { index ->
                ListItem {
                    OilItemView(state.pageItems[index])
                }
            }
        }

        PullRefreshIndicator(refreshing, refreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun OilItemView(item: Records) {
    Text(text = "当前里程:${item.currentMileage}\n本次加油量:${item.oilInjection}", color = Color.White)
}
