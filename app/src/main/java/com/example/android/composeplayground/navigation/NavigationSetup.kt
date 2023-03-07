package com.example.android.composeplayground.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android.composeplayground.*
import com.example.android.composeplayground.R

@Composable
fun NavigationSetup() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationScreen.MainScreen.route) {

        composable(route = NavigationScreen.MainScreen.route) {
            MainScreenChooser(navController = navController)
        }

        composable(route = NavigationScreen.Animations.route) {
            Column(Modifier.fillMaxSize()) {
                BoxAnimation()
                BoxAnimationBouncy()
            }
        }

        composable(route = NavigationScreen.MotionLayout.route) {
            //MOTION LAYOUT
            UsageOfMotionLayout()
        }

        composable(route = NavigationScreen.ButtonsAndSnack.route) {
            ButtonsAndSnackBar()
        }

        composable(route = NavigationScreen.Constraint.route) {
            ConstraintLayoutUsage()
        }

        composable(route = NavigationScreen.MoreAnimations.route) {
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(bottom = 16.dp)
            ) {
                ExampleAnimationVisibleOrNot(Modifier.weight(1f))
                ExampleAnimationState(Modifier.weight(1f))
                ExampleAnimationStateBouncy(Modifier.weight(1f))
                ExampleAnimationTransition(Modifier.weight(1f))
                AnimateContent(Modifier.weight(1f))
                AnimateContentSlide(Modifier.weight(1f))
            }

        }

        composable(route = NavigationScreen.CardComposable.route) {
            // cardTutorial

            val painter = painterResource(id = R.drawable.yo)
            val description = "Soy yo"
            val title = "Soy yo"
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(16.dp)
            ) {
                CardComposable(
                    painter = painter,
                    title = title,
                    contentDescription = description,
                )
            }
        }

        composable(route = NavigationScreen.ColorBoxState.route) {
            Column(modifier = Modifier.fillMaxSize()) {

                val boxesColorState = remember {
                    mutableStateOf(Color.Yellow)
                }

                ColorBoxState(
                    Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    boxesColorState.value = it
                }

                Box(
                    modifier = Modifier
                        .background(boxesColorState.value)
                        .weight(1f)
                        .fillMaxSize()
                )
            }
        }

        composable(route = NavigationScreen.TextStyling.route) {
            TextStyling()
        }

        composable(route = NavigationScreen.TheModifiers.route) {
            Modifiers()
        }

        composable(route = NavigationScreen.TheLazyListAndScrollableColumn.route) {
            Row(modifier = Modifier.fillMaxSize()) {
                LazyList(
                    Modifier
                        .weight(1f)
                        .background(Color.Green)
                )
                ScrollableColumn(
                    Modifier
                        .weight(1f)
                        .background(Color.LightGray)
                )
            }
        }

    }
}