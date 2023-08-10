package com.iostyle.just_compose

import android.content.res.Configuration
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.KeyframesSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.SnapSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun AnimatablePreview() {
    var isBig by remember { mutableStateOf(false) }

    val animatableDP = remember { Animatable(if (isBig) 300.dp else 128.dp, Dp.VectorConverter) }
    // color float 有扩展帮忙传入 converter
    val animatableColor = remember { Animatable(Color.Red) }
    val animatableFloat = remember { Animatable(8f) }

    // Compose中启动协程，recompose时，当参数列表有改变,就会重启
    LaunchedEffect(Unit) {
        // 直接切换，不播放动画
//        animatableDP.snapTo(if(isBig) 300.dp else 0.dp)
        animatableDP.animateTo(
            if (isBig) 128.dp else 300.dp,

            // 弹簧
//            spring(Spring.DampingRatioHighBouncy)
            // 阻尼比 刚度 可见性阈值(达到阈值骤停)
            spring(dampingRatio = 0.3f, stiffness = 1500f, visibilityThreshold = 1.dp)
            // 补间加速
//            TweenSpec(easing = FastOutSlowInEasing)
//            tween()
            // 切换 可配置延迟
//            SnapSpec(1000)
//            snap()
            // 关键帧
//            KeyframesSpec(KeyframesSpec.KeyframesSpecConfig<Dp>().apply {
//
//            })
//            keyframes {
//                // with 后面的插值器
//                128.dp at 0 with FastOutSlowInEasing
//                100.dp at 50 with FastOutLinearInEasing
//                200.dp at 100
//                durationMillis = 3000
//                delayMillis = 100
//            }
        )
    }

//    LaunchedEffect(key1 = isBig) {
//        animatableDP.animateTo(
//            200.dp,
//            spring(dampingRatio = 0.3f, stiffness = 1500f, visibilityThreshold = 1.dp)
//            , 2000.dp
//        )
//    }

    LaunchedEffect(key1 = isBig) {
        animatableDP.animateTo(
            if (isBig) 128.dp else 300.dp,
//            repeatable(3, tween(),
////                RepeatMode.Restart
//                RepeatMode.Reverse,
//                StartOffset(500,
////                    StartOffsetType.Delay
//                    StartOffsetType.FastForward
//                )
//            )
            infiniteRepeatable(tween(), RepeatMode.Reverse)
        )
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
fun AnimateDecay() {
    var isBig by remember { mutableStateOf(false) }
    val animatableDP = remember { Animatable(10.dp, Dp.VectorConverter) }

    // 样条衰减
    // 与 Android 自带滑动惯性相同, 像素密度越大，摩擦力越大，更快停止
    // 只能面向像素来使用，否则会受dpi影响导致摩擦力不同
//    splineBasedDecay()
//    val decay = rememberSplineBasedDecay<Dp>()

    // 指数衰减
    // frictionMultiplier 摩擦系数
    val decay = remember { exponentialDecay<Dp>(1f, 10f) }

    LaunchedEffect(key1 = isBig) {
        animatableDP.animateDecay(
            100.dp,
            decay
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(animatableDP.value)
            .background(Color.Gray)
            .clickable { isBig = !isBig }
    ) {
        Text(text = "${animatableDP.value}", color = Color.White)
    }
}

//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun Test() {
    Column {
//        AnimatablePreview()
//        AnimatePreview()
        AnimateDecay()
    }
}