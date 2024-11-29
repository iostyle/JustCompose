package com.iostyle.compoil.ui.page.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iostyle.compoil.data.Records
import com.iostyle.compoil.ui.page.ItemPreview
import com.iostyle.compoil.ui.page.ListViewPreviewParameterProvider
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
        LazyColumn(Modifier.fillMaxSize(), state = dataListLazySate) {
            items(dataList, key = { it.timestamp }) { item ->
                val index = dataList.indexOf(item)
                OilItemView(
                    Modifier.padding(0.dp, if (index == 0) 10.dp else 0.dp, 0.dp, if (index == dataList.size - 1) 10.dp else 0.dp),
                    item,
                    if (index > 0) dataList[index - 1] else null,
                    delete
                )
            }
        }

        PullRefreshIndicator(isRefreshing, refreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun OilItemView(modifier: Modifier, item: Records, lastItem: Records? = null, delete: (Records) -> Unit) {
    SwipeToDismissItem({
        delete(item)
    }) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(10.dp)
        ) {
            Text(
                text = "里程: ${item.currentMileage} 公里\n加油量: ${item.oilInjection} 升",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontSize = 13.sp
            )
            lastItem?.let {
                val oil = it.oilInjection / (item.currentMileage - it.currentMileage) * 100f
                val formattedOil = String.format(Locale.current.platformLocale, "%.2f", oil)
                Text(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    text = "油耗: $formattedOil 升/百公里", color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontSize = 13.sp
                )
            }
            Text(
                modifier = Modifier.align(Alignment.TopEnd),
                text = formatTimestamp(item.timestamp),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontSize = 13.sp
            )
        }

    }
}

fun formatTimestamp(timestamp: Long): String {
    val instant = Instant.ofEpochMilli(timestamp)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("yy/MM/dd EEEE", Locale.current.platformLocale)
    return localDateTime.format(formatter)
}

@Preview
@Composable
fun OilItemViewPreview(@PreviewParameter(ListViewPreviewParameterProvider::class) parameterProvider: ItemPreview) {
    LazyColumn {
        items(parameterProvider.records, key = { it.timestamp }) { item ->
            val index = parameterProvider.records.indexOf(item)
            OilItemView(Modifier, item, if (index > 0) parameterProvider.records[index - 1] else null, parameterProvider.delete)
        }
    }
}
