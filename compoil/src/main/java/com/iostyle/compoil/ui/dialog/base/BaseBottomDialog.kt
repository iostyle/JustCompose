package com.iostyle.compoil.ui.dialog.base

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.iostyle.compoil.R

abstract class BaseBottomDialog<T : ViewDataBinding>(val layout: Int) : DialogFragment() {

    protected lateinit var binding: T

    override fun onStart() {
        super.onStart()
        dialog?.window?.setGravity(Gravity.BOTTOM)
        val dm = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
        dialog?.window?.setLayout(dm.widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyleBottomIn)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<T>(inflater, layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    abstract fun initView()
}