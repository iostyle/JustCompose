package com.iostyle.just_compose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.iostyle.just_compose.ui.theme.JustComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JustComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
//                    color = Color.Red
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.ic_wallpaper),
                        contentDescription = "wallpaper",
                        contentScale = ContentScale.FillWidth,
                        alignment = Alignment.BottomCenter,
//                        modifier = Modifier
//                            .background(Color.Blue)
                    )
                    PC()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun GreetingPreview() {
    JustComposeTheme {
        Greeting("Android")
    }
}


@Composable
fun ColumnItems() {
    Column {
        Text(
            text = "Hello",
            Modifier
                .background(Color.Red)
                .width(100.dp)
        )
        Text(text = "World", Modifier.background(Color.Gray))
    }
}

@Composable
fun ConstraintLayoutTest() {
    ConstraintLayout(
        Modifier.background(Color.Gray)
//            .fillMaxWidth()
//            .width(200.dp)
//            .height(200.dp)
    ) {
        val (t0, t1, t2) = createRefs()
        Text(text = "Fuck", Modifier.constrainAs(t0) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        Text(text = "World",
            Modifier
                .constrainAs(t1) {
                    top.linkTo(t0.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(t2.start)
                }
                .border(
                    BorderStroke(
                        80.dp,
                        Brush.horizontalGradient(
                            arrayListOf(Color.Red, Color.Blue),
                            0f,
                            40f,
                            TileMode.Decal
                        )
                    )
                ))

        Text(text = "HAHA",
            Modifier
                .constrainAs(t2) {
                    top.linkTo(t0.bottom)
                    start.linkTo(t1.end)
                    end.linkTo(parent.end)
                }
                .padding(20.dp)
                .border(2.dp, Color.Cyan, RoundedCornerShape(8.dp)))


    }
}

@Composable
fun State() {
    var count by remember { mutableStateOf(1) }
    Button(onClick = {
        count++
    }) {
        Text(text = "$count")
    }
}

@Composable
fun CheckState() {
    ConstraintLayout(Modifier.background(Color.White)) {
        val (checkBox, tv) = createRefs()
        var checked by remember { mutableStateOf(false) }
        Checkbox(checked = checked, onCheckedChange = {
            checked = it
        }, Modifier.constrainAs(checkBox) {
            top.linkTo(parent.top)
        })

        Text(text = "$checked", Modifier.constrainAs(tv) {
            start.linkTo(checkBox.end)
            top.linkTo(checkBox.top)
            bottom.linkTo(checkBox.bottom)
        })


    }
}

@Composable
fun MessageCard(message: String) {
    Row(
        Modifier.padding(all = 8.dp)
//            .background(Color.Yellow)
    ) {
        AsyncImage(
            model = "https://avatars.githubusercontent.com/u/22219146?s=400&u=05fe262aa753f27abd7aa185ca430ac177472a0f&v=4",
            contentDescription = "avatar",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(
                    1.5.dp, MaterialTheme.colorScheme.secondary,
                    CircleShape
                )
        )

        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }

        val surfaceColor by animateColorAsState(
            targetValue = if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
            label = "ColorAnimation"
        )

        val context = LocalContext.current

        Column {
            Text(
                text = "Nickname",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium, shadowElevation = 5.dp, tonalElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {

                Text(
                    text = "$message",
                    //Modifier 代码顺序不同 显示有区别
                    Modifier
                        .clickable { isExpanded = !isExpanded }
                        .padding(8.dp),
//                        .background(Color.Gray)

//                        .clickable {
//                            Toast
//                                .makeText(context, "Test", Toast.LENGTH_LONG)
//                                .show()
//                        }
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
//                    color = Color.Blue
                )
            }

//            Button(onClick = {
//                Toast
//                    .makeText(context, "Test0", Toast.LENGTH_LONG)
//                    .show()
//            }, modifier = Modifier.clickable {
//                Toast
//                    .makeText(context, "Test1", Toast.LENGTH_LONG)
//                    .show()
//            }) {
//                Text(text = "测试", modifier = Modifier.clickable {
//                    Toast
//                        .makeText(context, "Test2", Toast.LENGTH_LONG)
//                        .show()
//                })
//            }


        }

    }
}

//@Preview(name = "Light Mode", showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun PreMC() {
    JustComposeTheme {
        Surface {
            MessageCard("Test")
        }
    }
}


@Composable
fun Conversation(messages: List<String>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message = message)
        }
    }
}

//@Preview(name = "Light Mode", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun PC() {
    JustComposeTheme {
//        Surface {
        Conversation(
            messages = arrayListOf(
                "hahahahahahahahahahahahahahahahahahahahahahahaha",
                "hohohohohohohohohohohohohohohohohohohohohohohohohohohohohoho",
                "hahahahahahahahahahahahahahahahahahahahahahahaha",
                "hohohohohohohohohohohohohohohohohohohohohohohohohohohohohoho",
            )
        )
//        }
    }
}