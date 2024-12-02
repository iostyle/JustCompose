package com.iostyle.compoil.ui.page

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.iostyle.compoil.ui.page.components.PullRefreshIndicatorView
import com.iostyle.compoil.ui.page.components.TopBar
import com.iostyle.compoil.ui.theme.JustComposeTheme

@Composable
fun PageScreen(
    state: PageState,
    actions: PageActions
) {
    Scaffold(
        modifier = Modifier,
        topBar = { TopBar(actions) },
    ) { innerPadding ->
        PullRefreshIndicatorView(
            modifier = Modifier.padding(innerPadding),
            dataList = state.pageItems,
            isRefreshing = state.isRefreshing,
            refresh = actions.onRefresh,
            delete = actions.onDelete
        )
    }
}

@Composable
@Preview(name = "Dark Theme", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light Theme", showSystemUi = true)
private fun PageScreenPreview(
    @PreviewParameter(PageStatePreviewParameterProvider::class)
    state: PageState
) {
    JustComposeTheme {
        PageScreen(
            state = state,
            actions = PageActions()
        )
    }
}
