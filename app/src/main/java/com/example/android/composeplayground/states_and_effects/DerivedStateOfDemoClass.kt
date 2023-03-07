package com.example.android.composeplayground.states_and_effects

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*

class DerivedStateOfDemoClass {

    @Composable
    fun DerivedStateOfDemo() {
        var counter by remember {
            mutableStateOf(0)
        }

        /*  // in this way every time the recomposition is made this operation gets made and
          // does all the process and the concatenation
          val counterText = "The counter is $counter"*/

        // using it this way means that a copy of the calculation is made
        // and any composable that needs the value uses the cache instead of re doing the computation
        // when the variable changes the cache needs to be made again
        val counterText by remember {
            derivedStateOf {
                "The counter is $counter"
            }
        }

        Button(onClick = { counter++ }) {
            Text(text = counterText)

        }

    }

}