package com.iostyle.compoil.ui.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PageRoute(
    coordinator: PageCoordinator = rememberPageCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(PageState())

    // UI Actions
    val actions = rememberPageActions(coordinator)

    // UI Rendering
    PageScreen(uiState, actions)
}


@Composable
fun rememberPageActions(coordinator: PageCoordinator): PageActions {
    return remember(coordinator) {
        PageActions(
            onRefresh = coordinator::handleRefresh,
            onFloatingButtonClick = coordinator::handleFloatingButtonClick,
        )
    }
}
