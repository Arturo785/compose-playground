package com.example.android.composeplayground.states_and_effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import kotlinx.coroutines.delay
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.flow

class ProduceStatesDemo {


    // this is a way to return a producer in a compose way, we can also use flows to do this
    @Composable
    private fun produceStateDemos(countUpTo: Int): State<Int> {
        return produceState(initialValue = 0) {
            while (value < countUpTo) {
                delay(1000)
                value++
            }
        }
    }

    @Composable
    private fun ProduceStatesFlow(countUpTo: Int): State<Int> {
        var value = 0
        return flow<Int> {
            while (value < countUpTo) {
                delay(1000)
                value++
                emit(value)
            }
        }.collectAsState(initial = value)
    }

}