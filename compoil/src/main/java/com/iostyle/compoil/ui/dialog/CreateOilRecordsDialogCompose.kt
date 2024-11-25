package com.iostyle.compoil.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import com.iostyle.compoil.R
import com.iostyle.compoil.ui.dialog.base.BaseBottomDialogCompose

class CreateOilRecordsDialogCompose : BaseBottomDialogCompose() {

    companion object {
        fun show(fragmentManager: FragmentManager) {
            CreateOilRecordsDialogCompose().show(fragmentManager, null)
        }
    }

    @Preview
    @Composable
    override fun InitView() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
//                .shadow(
//                    elevation = 15.dp,
//                    shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
//                )
                .background(color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
//                .border(
//                    width = 2.dp,
//                    color = Color(requireContext().getColor(R.color.tiktok_cyan)),
//                    shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)
//                )
                .padding(10.dp, 20.dp, 10.dp, 10.dp)
        ) {
            Text(text = "OK")
            Text(text = "CreateOilRecordsDialogCompose", color = MaterialTheme.colorScheme.onSurface)
        }
    }

}