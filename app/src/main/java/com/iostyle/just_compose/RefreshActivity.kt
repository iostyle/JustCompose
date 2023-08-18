package com.iostyle.just_compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ListItem
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.pullRefreshIndicatorTransform
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RefreshActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            PullRefreshIndicatorPreview()
//            RememberPullRefreshStatePreview()
//            ProgressPreview()
            CirclePreview()
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
//   @Preview
    @Composable
    fun PullRefreshIndicatorPreview() {
        val refreshScope = rememberCoroutineScope()
        var refreshing by remember { mutableStateOf(false) }
        var itemCount by remember { mutableStateOf(15) }

        fun refresh() = refreshScope.launch {
            refreshing = true
            delay(1500)
            itemCount += 5
            refreshing = false
        }

        val state = rememberPullRefreshState(refreshing, ::refresh)

        Box(Modifier.pullRefresh(state)) {
            LazyColumn(Modifier.fillMaxSize()) {
                if (!refreshing) {
                    items(itemCount) {
                        ListItem { Text(text = "Item ${itemCount - it}") }
                    }
                }
            }

            //
            PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
//    @Preview
    @Composable
    fun RememberPullRefreshStatePreview() {
        val refreshScope = rememberCoroutineScope()
        var refreshing by remember { mutableStateOf(false) }
        var itemCount by remember { mutableStateOf(15) }

        fun refresh() = refreshScope.launch {
            refreshing = true
            delay(1500)
            itemCount += 5
            refreshing = false
        }

        val state = rememberPullRefreshState(refreshing, ::refresh)

        Box(Modifier.pullRefresh(state)) {
            LazyColumn(Modifier.fillMaxSize()) {
//                if (!refreshing) {
                items(itemCount) {
                    ListItem { Text(text = "Item ${itemCount - it}") }
                }
//                }
            }

            PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
//@Preview
@Composable
fun ProgressPreview() {
    val refreshScope = rememberCoroutineScope()
    val threshold = with(LocalDensity.current) { 160.dp.toPx() }

    var refreshing by remember { mutableStateOf(false) }
    var itemCount by remember { mutableStateOf(15) }
    var currentDistance by remember { mutableStateOf(0f) }

    val progress = currentDistance / threshold

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        itemCount += 5
        refreshing = false
    }

    fun onPull(pullDelta: Float): Float = when {
        refreshing -> 0f
        else -> {
            val newOffset = (currentDistance + pullDelta).coerceAtLeast(0f)
            val dragConsumed = newOffset - currentDistance
            currentDistance = newOffset
            dragConsumed
        }
    }

    fun onRelease(velocity: Float): Float {
        if (refreshing) return 0f // Already refreshing - don't call refresh again.
        if (currentDistance > threshold) refresh()

        refreshScope.launch {
            animate(initialValue = currentDistance, targetValue = 0f) { value, _ ->
                currentDistance = value
            }
        }

        // Only consume if the fling is downwards and the indicator is visible
        return if (velocity > 0f && currentDistance > 0f) {
            velocity
        } else {
            0f
        }
    }

    Box(Modifier.pullRefresh(::onPull, ::onRelease)) {
        LazyColumn {
            if (!refreshing) {
                items(itemCount) {
                    ListItem { Text(text = "Item ${itemCount - it}") }
                }
            }
        }

        // Custom progress indicator
        AnimatedVisibility(visible = (refreshing || progress > 0)) {
            if (refreshing) {
                LinearProgressIndicator(Modifier.fillMaxWidth())
            } else {
                LinearProgressIndicator(progress, Modifier.fillMaxWidth())
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun CirclePreview() {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    var itemCount by remember { mutableStateOf(15) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        itemCount += 5
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)
    val rotation = animateFloatAsState(state.progress * 120)

    Box(
        Modifier
            .fillMaxSize()
            .pullRefresh(state)
    ) {
        LazyColumn {
//            if (!refreshing) {
            items(itemCount) {
                ListItem { Text(text = "Item ${itemCount - it}") }
            }
//            }
        }

        Surface(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.TopCenter)
                .pullRefreshIndicatorTransform(state)
                .rotate(rotation.value),
            shape = RoundedCornerShape(10.dp),
            color = Color.DarkGray,
            tonalElevation = if (state.progress > 0 || refreshing) 20.dp else 0.dp,
        ) {
            Box {
                if (refreshing) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(25.dp),
                        color = Color.White,
                        strokeWidth = 3.dp
                    )
                }
            }
        }
    }
}