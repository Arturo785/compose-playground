package com.example.android.composeplayground

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ExampleAnimationVisibleOrNot(modifier: Modifier = Modifier) {
    var isVisible by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                isVisible = !isVisible
            }
        ) {
            Text(text = "Toggle")
        }

        AnimatedVisibility(
            visible = isVisible,
            enter = slideInHorizontally() + fadeIn(), // we can customize enter and exit transitions
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(modifier = Modifier.background(Color.Red))
        }
    }
}


@Composable
fun ExampleAnimationState(modifier: Modifier = Modifier) {
    var isVisible by remember {
        mutableStateOf(false)
    }

    var isRound by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                isVisible = !isVisible
                isRound = !isRound
            }
        ) {
            Text(text = "Toggle")
        }

        val borderRadius by animateIntAsState(
            targetValue = if (isRound) 100 else 0,
            animationSpec =
            tween(
                durationMillis = 2000,
                delayMillis = 500
            ), // moves the duration and how fast it happens

        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(borderRadius))
                .background(Color.Red)
        )
    }
}


@Composable
fun ExampleAnimationStateBouncy(modifier: Modifier = Modifier) {
    var isVisible by remember {
        mutableStateOf(false)
    }

    var isRound by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                isVisible = !isVisible
                isRound = !isRound
            }
        ) {
            Text(text = "Toggle")
        }

        val borderRadius by animateIntAsState(
            targetValue = if (isRound) 40 else 20,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(borderRadius))
                .background(Color.Red)
        )
    }
}


// This way is used when we want to animate multiple values bases in a single state,
// it's a preferred way although is not mandatory, it can be done separately like the previous examples
@Composable
fun ExampleAnimationTransition(modifier: Modifier = Modifier) {
    var isVisible by remember {
        mutableStateOf(false)
    }

    var isRound by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                isVisible = !isVisible
                isRound = !isRound
            }
        ) {
            Text(text = "Toggle")
        }

        // This way is used when we want to animate multiple values bases in a single state
        // the transition takes care to check the target state that triggers the animation
        val transition = updateTransition(
            targetState = isRound,
            label = null
        )


        // this both two get transformed depending in the transition above
        val borderRadius by transition.animateInt(
            transitionSpec = {
                tween(200)
            },
            label = "borderRadius",
            targetValueByState = { isRoundInternal ->
                if (isRoundInternal) 100 else 0
            }
        )

        // this both two get transformed depending in the transition above
        val colorTransition by transition.animateColor(
            transitionSpec = {
                tween(1000)
            },
            label = "colorTransition",
            targetValueByState = { isRoundInternal ->
                if (isRoundInternal) Color.Green else Color.Magenta
            }
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(borderRadius))
                .background(colorTransition)
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateContent(modifier: Modifier = Modifier) {
    var isVisible by remember {
        mutableStateOf(false)
    }

    var isRound by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                isVisible = !isVisible
                isRound = !isRound
            }
        ) {
            Text(text = "Toggle")
        }

        AnimatedContent(
            targetState = isVisible,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), content = { isVisibleInner ->
                if (isVisibleInner) {
                    Box(modifier = Modifier.background(Color.Green))
                } else {
                    Box(modifier = Modifier.background(Color.Red))
                }
            }, transitionSpec = {
                fadeIn() with fadeOut()
            }
        )

    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateContentSlide(modifier: Modifier = Modifier) {
    var isVisible by remember {
        mutableStateOf(false)
    }

    var isRound by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                isVisible = !isVisible
                isRound = !isRound
            }
        ) {
            Text(text = "Toggle")
        }

        AnimatedContent(
            targetState = isVisible,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), content = { isVisibleInner ->
                if (isVisibleInner) {
                    Box(modifier = Modifier.background(Color.Green))
                } else {
                    Box(modifier = Modifier.background(Color.Red))
                }
            }, transitionSpec = {
                slideInHorizontally(initialOffsetX = {
                    -it // starts from minus the size of the box which simulates the sliding
                }) with slideOutHorizontally(targetOffsetX = {
                    it // replaces the old view
                })
            }
        )

    }
}
