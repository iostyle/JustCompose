package com.iostyle.compoil.ui.page

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.fragment.app.FragmentManager
import com.iostyle.compoil.data.Records
import com.iostyle.compoil.data.getCacheRecords


/**
 * UI State that represents PageScreen
 **/
data class PageState(
    val pageItems: MutableList<Records> = mutableListOf(),
    var isRefreshing: Boolean = false
)

/**
 * Page Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class PageActions(
    val onRefresh: () -> Unit = {},
    val onDelete: (Records) -> Unit = {},
    val onFloatingButtonClick: (fragmentManager: FragmentManager) -> Unit = {},
)

/**
 * PreviewParameter Provider for PageScreen Preview
 * Add values to the sequence to see the preview in different states
 **/
class PageStatePreviewParameterProvider : PreviewParameterProvider<PageState> {
    override val values: Sequence<PageState>
        get() = sequenceOf(

        )
}
