package com.example.android.composeplayground

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun BoxAnimation() {
    var sizeState by remember {
        mutableStateOf(200.dp)
    }

    //this animates the size in a more smooth way
    val sizeAnim by animateDpAsState(
        targetValue = sizeState,
        tween(
            durationMillis = 3000,
            delayMillis = 300,
            easing = LinearOutSlowInEasing
        ), // animation curve

    )

    Box(
        modifier = Modifier
            .size(sizeAnim)
            .background(Color.Red),
        contentAlignment = Alignment.Center,
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Button(onClick = { sizeState += 50.dp }) {
                Text(text = "Increase size")
            }

            Button(onClick = { sizeState -= 50.dp }) {
                Text(text = "Decrease size")
            }
        }
    }
}

@Composable
fun BoxAnimationBouncy() {
    var sizeState by remember {
        mutableStateOf(200.dp)
    }

    //this animates the size in a more smooth way
    val sizeAnim by animateDpAsState(
        targetValue = sizeState,
        spring(Spring.DampingRatioHighBouncy),

        // this plays with the animation and you can controll what happens at timestamps
        /*      keyframes {
                  durationMillis = 5000
                  sizeState at 0 with LinearEasing
                  sizeState * 1.5f at 1000 with FastOutLinearInEasing
                  sizeState * 2f at 3000 with LinearEasing
              }*/

    )


    // this is a transition that will repeat forever we can change the interval of it
    val infiniteTransition = rememberInfiniteTransition()
    val colorRepeat by infiniteTransition.animateColor(
        initialValue = Color.Black,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .size(sizeAnim)
            .background(colorRepeat),
        contentAlignment = Alignment.Center,
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Button(onClick = { sizeState += 50.dp }) {
                Text(text = "Increase size")
            }

            Button(onClick = { sizeState -= 50.dp }) {
                Text(text = "Decrease size")
            }
        }
    }
}