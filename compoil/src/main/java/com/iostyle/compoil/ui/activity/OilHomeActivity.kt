package com.iostyle.compoil.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iostyle.compoil.R
import com.iostyle.compoil.bean.Records
import com.iostyle.compoil.ui.dialog.CreateOilRecordsDialogCompose
import com.iostyle.compoil.ui.theme.JustComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class OilHomeActivity : AppCompatActivity() {

    private val recordsList: MutableList<Records> by lazy { mutableListOf() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JustComposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    // TODO 剥离组件并触发刷新
//                    floatingActionButton = {
//                        FloatAddRecordsButton(modifier = Modifier)
//                    },
                    topBar = { TopBar() }
                ) { innerPadding ->
                    Box {
                        PullRefreshIndicatorView(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun PullRefreshIndicatorView(modifier: Modifier = Modifier) {
        val refreshScope = rememberCoroutineScope()
        var refreshing by remember { mutableStateOf(false) }
        var itemCount by remember { mutableStateOf(0) }

        fun refresh() = refreshScope.launch {
            refreshing = true
            delay(1500)
            itemCount = recordsList.size
            refreshing = false
        }

        val state = rememberPullRefreshState(refreshing, ::refresh)

        Box(modifier.pullRefresh(state)) {
            LazyColumn(modifier.fillMaxSize()) {
                items(itemCount) { index ->
                    ListItem {
                        OilItemView(index)
                    }
                }
            }

            PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))

            FloatAddRecordsButton(modifier = modifier, ::refresh)
        }

    }

    @Preview
    @Composable
    fun OilItemView(index: Int = 0) {
        Text(text = "${recordsList[index].currentMileage}", color = Color.White)
    }


    @Preview
    @Composable
    fun FloatAddRecordsButton(modifier: Modifier = Modifier, refresh: (() -> Unit)? = null) {

        var show by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 0.dp, 16.dp, 30.dp), contentAlignment = Alignment.BottomEnd
        ) {
            Button(
                onClick = {
//                show = show.not()
                    show = true
                }, shape = RoundedCornerShape(20.dp), modifier = modifier
                    .width(40.dp)
                    .height(40.dp),
                contentPadding = PaddingValues(0.dp)
            ) { Text(text = "+", fontSize = 20.sp, textAlign = TextAlign.Center) }
        }

        if (show) {
            AlertDialog(
                onDismissRequest = { show = false },
                confirmButton = {
                    Button(
                        onClick = {
                            val records = Records(System.currentTimeMillis(), Random.nextInt(), 8.8f)
                            recordsList.add(records)
                            show = false
                            refresh?.invoke()
                        }) {
                        Text(text = "Confirm")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        CreateOilRecordsDialogCompose.show(supportFragmentManager)
                        show = false
                    }) {
                        Text(text = "Dismiss")
                    }
                },
                title = { Text(text = "Dialog Title") },
                text = { Text(text = "Dialog Content") },
            )
        }
    }

    @Preview
    @Composable
    fun TopBar() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .height(50.dp)
                .background(MaterialTheme.colorScheme.onSecondary)
                .padding(10.dp, 0.dp)
        ) {
            Text(text = "Title", modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center))
            Button(
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.CenterEnd),
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                contentPadding = PaddingValues(0.dp),
            ) {
                Image(modifier = Modifier.size(25.dp), painter = painterResource(R.drawable.ic_setting), contentDescription = "Setting")
            }
        }
    }
}