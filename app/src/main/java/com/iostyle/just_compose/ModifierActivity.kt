package com.iostyle.just_compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

interface TestInterface {

    // 伴生对象返回本身的话，代码里可以直接使用接口名即是实现了接口的单例对象
    companion object : TestInterface {
        var age = 0
    }


}

//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun Preview() {
    Modifier.fillMaxWidth()
    TestInterface.age = 10
    println("age: ${TestInterface.age}")

    Modifier.composed { Modifier.padding(8.dp) }
}


//        object : MeasureResult {
//            // 测量基准线
//            override val alignmentLines: Map<AlignmentLine, Int>
//                get() = TODO("Not yet implemented")
//            override val height: Int
//                get() = placeable.height
//            override val width: Int
//                get() = placeable.width
//
//            // 设置摆放内部组件
//            override fun placeChildren() {
//                TODO("Not yet implemented")
//            }
//        }

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun LayoutPreview() {
//    LayoutModifier
    Box(
        modifier = Modifier
            .background(Color.Gray)
            .fillMaxWidth()
    ) {
        Text(text = "Test",
            // 对自身测量出的尺寸和位置进行修改的功能
            Modifier.layout { measurable, constraints ->
                // ** 测量
                val placeable = measurable.measure(constraints)
                // ** 保存测量结果
                layout(placeable.width, placeable.height) {
                    // ** 位置修改 只是修改自身，不能精确布局每一个子view，后续需要使用Layout
                    placeable.placeRelative(0, 0)
                }
            }
        )
    }

}




