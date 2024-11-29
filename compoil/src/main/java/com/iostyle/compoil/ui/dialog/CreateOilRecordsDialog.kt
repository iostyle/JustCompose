package com.iostyle.compoil.ui.dialog

import android.content.DialogInterface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentManager
import com.iostyle.compoil.ui.dialog.base.BaseBottomDialogCompose
import java.util.regex.Pattern

class CreateOilRecordsDialog : BaseBottomDialogCompose() {

    private var callback: ICreateOilRecords? = null

    companion object {
        fun show(fragmentManager: FragmentManager, callback: ICreateOilRecords) {
            CreateOilRecordsDialog().apply {
                this.callback = callback
                show(fragmentManager, null)
            }
        }
    }

    interface ICreateOilRecords {
        fun callback(currentMileage: Int, oilInjection: Float)
    }

    @Preview
    @Composable
    override fun InitView() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
                .padding(20.dp, 20.dp, 20.dp, 20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "添加一笔加油记录", fontSize = 24.sp, color = MaterialTheme.colorScheme.onSurface,
                )
                Column(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 20.dp, 0.dp, 0.dp)
                ) {
                    val mileageState = remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = mileageState.value,
                        onValueChange = { mileageState.value = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(0.dp, 0.dp, 0.dp, 10.dp),
                        label = { Text(text = "当前里程", fontSize = 14.sp) },
                        placeholder = { Text(text = "请输入当前里程（单位公里）", fontSize = 14.sp) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        visualTransformation = NumberFilterTransformation(),
                    )

                    val oilInjectionState = remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = oilInjectionState.value,
                        onValueChange = { oilInjectionState.value = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(0.dp, 10.dp, 0.dp, 0.dp),
                        label = { Text(text = "本次加油", fontSize = 14.sp) },
                        placeholder = { Text(text = "请输入本次加油量（单位升）", fontSize = 14.sp) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        ),
                        // fixme 少一个合适的正则参数
                        visualTransformation = NumberFilterTransformation(),
                    )

                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(0.dp, 20.dp, 0.dp, 0.dp), onClick = {
                            callback?.callback(mileageState.value.toInt(), oilInjectionState.value.toFloat())
                            dismissAllowingStateLoss()
                        }) { Text(text = "添加", color = MaterialTheme.colorScheme.onPrimary) }
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        callback = null
    }
}

class NumberFilterTransformation(regex: String = "[^\\d]") : VisualTransformation {
    private val pattern = Pattern.compile(regex)

    override fun filter(text: AnnotatedString): TransformedText {
        val filteredText = pattern.matcher(text.text).replaceAll("")
        return TransformedText(
            text = AnnotatedString(filteredText),
            offsetMapping = SimpleOffsetMapping(text.text.length, filteredText.length)
        )
    }
}

class SimpleOffsetMapping(private val originalLength: Int, private val transformedLength: Int) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        return if (offset < 0 || offset > originalLength) {
            throw IllegalArgumentException("Invalid offset: $offset")
        } else {
            offset.coerceAtMost(transformedLength)
        }
    }

    override fun transformedToOriginal(offset: Int): Int {
        return if (offset < 0 || offset > transformedLength) {
            throw IllegalArgumentException("Invalid offset: $offset")
        } else {
            offset.coerceAtMost(originalLength)
        }
    }
}