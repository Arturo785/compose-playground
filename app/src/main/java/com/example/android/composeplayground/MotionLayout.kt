package com.example.android.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import kotlinx.coroutines.launch





@OptIn(ExperimentalMotionApi::class)
@Composable
fun ProfileHeader(progress: Float) {

    // uses a json5 file in the raw resources folder
    // it manages everything like constraint layout
    // like this
    /*
    *
    *   height: 40,
        start: ['parent', 'start', 16], to parentStart
        top: ['parent', 'top', 16],  // tio parentTop
        custom: {
          background: '#08ff04'
        }
      },
      username: {
        top: ['profile_pic', 'top'], to top of profile pic
        bottom: ['profile_pic', 'bottom'],
        start: ['profile_pic', 'end', 16]*/ // the start into the end of the profile picture id we gave
    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }
    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier.fillMaxWidth()
    ) {
        val properties = motionProperties(id = "profile_pic")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .layoutId("box")
        )
        Image(
            painter = painterResource(id = R.drawable.yo),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = properties.value.color("background"),
                    shape = CircleShape
                )
                .layoutId("profile_pic")
        )
        Text(
            text = "Philipp Lackner",
            fontSize = 24.sp,
            modifier = Modifier.layoutId("username"),
            color = properties.value.color("background")
        )
    }
}

@Composable
fun AnimateWithoutlayout(){
    val crs = rememberCoroutineScope()
    val density = LocalDensity.current
    val posX1 = remember { Animatable(initialValue = 0.5f) }
    var sliderX1 by remember { mutableStateOf(0.5f) }

    Slider(
        value = sliderX1,
        onValueChange = { sliderX1 = it; crs.launch { posX1.animateTo(it) } },
    )
    // the composable to animate
/*    YourComposable(
        modifier = Modifier,
        centerX = with(density) { (yourComposableSize.dp.toPx() / 2) * sliderX1 },
    )*/


}