package com.iostyle.just_compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.gyf.immersionbar.ktx.immersionBar

class EffectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.transparent)
            statusBarDarkFont(true, 0.2f)
            navigationBarColor(R.color.transparent)
            fitsSystemWindows(true)
        }
        setContent {
            EffectPreview()
        }
    }


}


@Composable
fun HomeScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onStart: () -> Unit, // Send the 'started' analytics event
    onStop: () -> Unit // Send the 'stopped' analytics event
) {
    // Safely update the current lambdas when a new one is provided
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                currentOnStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                currentOnStop()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    /* Home screen content */
}

@Composable
fun EffectPreview() {
    var show by remember { mutableStateOf(true) }

    Button(onClick = { show = !show }) {
        Text(text = "点击")
        if (show) {
            Text(text = "嘿嘿")
        }
    }

    SideEffect {
        println("SideEffect")
    }

    DisposableEffect(key1 = show, effect = {
        println("DisposableEffect $show")
        onDispose {
            println("DisposableEffect onDispose")
        }
    })

    // 当 composable 启动(显示)完成后，创建并执行协程；当参数列表变化时，停止上一个协程，然后创建并执行一个新的协程
    // 与 DisposableEffect 实现方式相同，在 onDispose 上层调用时，代码为 job.cancel job = null
    LaunchedEffect(key1 = show, block = {
        println("LaunchedEffect $show")
    })

    // 启动协程
//    rememberCoroutineScope()
}

