package com.iostyle.compoil.ui.dialog.base

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.DialogFragment
import com.iostyle.compoil.R
import com.iostyle.compoil.ui.theme.JustComposeTheme

abstract class BaseBottomDialogCompose() : DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyleBottomIn)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply{
            setContent {
                JustComposeTheme {
                    InitView()
                }
            }
        }
    }

    @Composable
    abstract fun InitView()
}