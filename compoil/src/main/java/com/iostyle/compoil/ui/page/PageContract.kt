package com.iostyle.compoil.ui.page

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.fragment.app.FragmentManager
import com.iostyle.compoil.bean.Records


/**
 * UI State that represents PageScreen
 **/
data class PageState(
//    val pageItems: MutableList<Records> = mutableListOf(),
    val pageItems: MutableList<Records> = mutableListOf(
        Records(System.currentTimeMillis(), 1234, 8.8f),
        Records(System.currentTimeMillis(), 1334, 9.8f),
    ),
)

/**
 * Page Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class PageActions(
    val onRefresh: suspend () -> Unit = {},
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
