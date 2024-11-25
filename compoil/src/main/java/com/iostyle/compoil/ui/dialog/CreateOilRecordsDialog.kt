package com.iostyle.compoil.ui.dialog

import androidx.fragment.app.FragmentManager
import com.iostyle.compoil.R
import com.iostyle.compoil.databinding.DialogCreateOilRecordsBinding
import com.iostyle.compoil.ui.dialog.base.BaseBottomDialog

class CreateOilRecordsDialog : BaseBottomDialog<DialogCreateOilRecordsBinding>(R.layout.dialog_create_oil_records) {

    companion object {
        fun show(fragmentManager: FragmentManager) {
            CreateOilRecordsDialog().show(fragmentManager, null)
        }
    }

    override fun initView() {
    }
}