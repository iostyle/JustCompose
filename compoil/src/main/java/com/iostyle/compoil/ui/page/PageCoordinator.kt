package com.iostyle.compoil.ui.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.fragment.app.FragmentManager
import org.koin.androidx.compose.koinViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class PageCoordinator(
    val viewModel: PageViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    fun handleRefresh() {
        viewModel.refreshPage()
    }

    fun handleFloatingButtonClick(fragmentManager: FragmentManager) {
        viewModel.floatingButtonClick(fragmentManager)
    }
}

@Composable
fun rememberPageCoordinator(
    viewModel: PageViewModel = koinViewModel()
): PageCoordinator {
    return remember(viewModel) {
        PageCoordinator(
            viewModel = viewModel
        )
    }
}