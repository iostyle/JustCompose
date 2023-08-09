package com.iostyle.just_compose

import android.content.res.Configuration
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun AnimatePreview() {
    var isBig by remember { mutableStateOf(false) }

    // 不需要 remember。 数据变化时，内部启动协程不断修改值并不断刷新
    val dynamicSize by animateDpAsState(if (isBig) 280.dp else 180.dp, label = "")
    val dynamicColor by animateColorAsState(if (isBig) Color.Red else Color.Green, label = "")

    Box(
        modifier = Modifier
            .size(size = dynamicSize)
            .background(dynamicColor)
            .clickable { isBig = !isBig }
    ) {
        Text(text = "$dynamicSize")
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun AnimatablePreview() {
    var isBig by remember { mutableStateOf(false) }

    val animatableDP = remember { Animatable(if (isBig) 300.dp else 128.dp, Dp.VectorConverter) }
    // color float 有扩展帮忙传入 converter
    val animatableColor = remember { Animatable(Color.Red) }
    val animatableFloat = remember { Animatable(8f) }

    // Compose中启动协程，recompose时，当参数列表有改变,就会重启
    LaunchedEffect(key1 = isBig) {
        // 直接切换，不播放动画
//        animatableDP.snapTo(if(isBig) 300.dp else 0.dp)
        animatableDP.animateTo(if (isBig) 128.dp else 300.dp)
    }

    Box(
        modifier = Modifier
            .size(size = animatableDP.value)
            .background(Color.Gray)
            .clickable { isBig = !isBig }
    ) {
        Text(text = "${animatableDP.value}", color = Color.White)
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun Test() {
    Column {
        AnimatePreview()
        AnimatablePreview()
    }
}