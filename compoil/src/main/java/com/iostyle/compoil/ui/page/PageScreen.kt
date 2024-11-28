package com.iostyle.compoil.ui.page

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.iostyle.compoil.ui.page.components.FloatingButton
import com.iostyle.compoil.ui.page.components.PullRefreshIndicatorView

@Composable
fun PageScreen(
    state: PageState,
    actions: PageActions
) {
    Scaffold(
        modifier = Modifier,
        floatingActionButton = { FloatingButton(state = state, actions = actions) },
        floatingActionButtonPosition = FabPosition.EndOverlay
    ) { innerPadding ->
        PullRefreshIndicatorView(
            modifier = Modifier.padding(innerPadding),
            dataList = state.pageItems,
            isRefreshing = state.isRefreshing,
            refresh = actions.onRefresh
        )
    }
}

@Composable
@Preview(name = "Page", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Page", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun PageScreenPreview(
    @PreviewParameter(PageStatePreviewParameterProvider::class)
    state: PageState
) {
    PageScreen(
        state = state,
        actions = PageActions()
    )
}
