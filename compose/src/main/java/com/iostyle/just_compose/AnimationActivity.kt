package com.iostyle.just_compose

import android.content.res.Configuration
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
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
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.splineBasedDecay
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
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

//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun AnimateDecay() {
    var isBig by remember { mutableStateOf(false) }
    val animatableDP = remember { Animatable(0.dp, Dp.VectorConverter) }

    // 样条衰减
    // 与 Android 自带滑动惯性相同, 像素密度越大，摩擦力越大，更快停止
    // 只能面向像素来使用，否则会受dpi影响导致摩擦力不同
//    splineBasedDecay()
//    val decay = rememberSplineBasedDecay<Dp>()

    // 指数衰减
    // frictionMultiplier 摩擦系数
    val decay = remember { exponentialDecay<Dp>(1f, 10f) }

    var padding2 by remember { mutableStateOf(animatableDP.value) }

    LaunchedEffect(key1 = isBig) {
        animatableDP.animateDecay(
            100.dp,
            decay
        ) {
            padding2 = value
        }
    }

    // 边界
    animatableDP.updateBounds(0.dp, 100.dp)

    Column(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .padding(animatableDP.value, 0.dp, 0.dp, 0.dp)
                .size(280.dp)
                .clickable { isBig = !isBig }
                .background(Color.Blue)
        ) {
            Text(text = "${animatableDP.value}", color = Color.White)
        }
        Box(
            modifier = Modifier
                .padding(padding2, 0.dp, 0.dp, 0.dp)
                .size(280.dp)
                .background(Color.Gray)
                .clickable { isBig = !isBig }
                .background(Color.Gray)
        ) {
            Text(text = "${animatableDP.value}", color = Color.White)
        }
    }
}

//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun Transition() {
    var isBig by remember { mutableStateOf(false) }
    val bigTransition = updateTransition(targetState = isBig, label = "big")

    val dynamicSize by bigTransition.animateDp(
        { spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMediumLow) },
        label = "size"
    ) { if (it) 280.dp else 180.dp }
    val dynamicColor by bigTransition.animateColor(label = "color") { if (it) Color.Red else Color.Magenta }
    val dynamicCorner by bigTransition.animateDp(label = "corner") { if (it) 100.dp else 0.dp }

    // 无限循环
    val infiniteTransition = rememberInfiniteTransition("infinite")

    Box(
        modifier = Modifier
            .size(size = dynamicSize)
            .clip(RoundedCornerShape(dynamicCorner))
            .background(dynamicColor)
            .clickable { isBig = !isBig }
    ) {
        Text(text = "$dynamicSize \n$dynamicColor")

    }
}

@OptIn(ExperimentalAnimationApi::class)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun AnimatedVisibilityPreview() {
    Column(Modifier.fillMaxWidth()) {
        var shown by remember {
            mutableStateOf(true)
        }
        // 带动画的显示隐藏控制
        AnimatedVisibility(
            shown,
//            enter = fadeIn(tween(2000), initialAlpha = 0.2f) + expandVertically()
//            enter = slideIn { IntOffset(-it.width, -it.height) }
//            enter = slideInHorizontally { -it }
//            enter = expandIn(tween(3000), expandFrom = Alignment.TopStart)
//            enter = expandIn(tween(3000), expandFrom = Alignment.TopEnd)
//            enter = expandIn(tween(3000), expandFrom = Alignment.BottomEnd)
//            enter = expandIn(tween(3000), expandFrom = Alignment.BottomStart, clip = true)
//            enter = scaleIn(transformOrigin = TransformOrigin(0f, 1f))
            enter = fadeIn(tween(2000), initialAlpha = 0.2f)
                    + expandIn(tween(2000), expandFrom = Alignment.BottomEnd),
            exit = fadeOut(tween(2000), targetAlpha = 0.5f)
                    + shrinkOut(tween(2000), shrinkTowards = Alignment.BottomEnd)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_wallpaper),
                contentDescription = "wallpaper",
                contentScale = ContentScale.FillWidth,
            )
        }
        Button(onClick = { shown = !shown }) {
            Text(text = "$shown")
        }
    }
}

//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun CrossFadePreview() {
    var shown by remember {
        mutableStateOf(true)
    }
    Column(Modifier.fillMaxWidth()) {
        // ViewPager ? 😁
        // 尺寸改变动画是瞬变
        Crossfade(targetState = shown, label = "crossFade") {
            if (it) {
                Transition()
            } else {
                AnimatedVisibilityPreview()
            }
        }
        Button(onClick = { shown = !shown }) {
            Text(text = "crossFade $shown")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun AnimatedContentPreview() {
    var shown by remember {
        mutableStateOf(true)
    }
    Column(Modifier.fillMaxWidth()) {
        // 尺寸改变动画是渐变的
        AnimatedContent(targetState = shown, label = "AnimatedContent",
            transitionSpec = {
//                fadeIn() with fadeOut()
                (fadeIn(tween(2000), initialAlpha = 0.2f)
                + expandIn(tween(2000,2000), expandFrom = Alignment.BottomEnd)) with (fadeOut(tween(2000), targetAlpha = 0.5f)
                + shrinkOut(tween(2000), shrinkTowards = Alignment.BottomEnd))
            }
        ) {
            if (it) {
                Transition()
            } else {
                AnimatedVisibilityPreview()
            }
        }
        Button(onClick = { shown = !shown }) {
            Text(text = "crossFade $shown")
        }
    }
}