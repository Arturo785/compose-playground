package com.example.android.composeplayground

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import kotlinx.coroutines.flow.collectLatest

class ComposeEffectHandlers {

    private var i = 0

    // this explain what is a side effect and how to avoid it
    //https://www.youtube.com/watch?v=gxWcfz3V2QE&list=PLQkwcJG4YTCSpJ2NLhDTHhi6XBNfk9WiC&index=10

    // in overall a side effect is code that executes inside a composable when it does not belong there
    // and you do not have control over it like this example


    @Composable
    private fun SideEffect() {
        var textState by remember {
            mutableStateOf("")
        }

        Button(onClick = { textState += "#" }) {
            i++
            Text(text = textState) // when the textState changes this needs to be recomposed
            // and that leeds into i also changing but i does not belong inside the recomposition logic
        }
    }

    @Composable
    private fun SideEffectCall() {
        androidx.compose.runtime.SideEffect {
            println("Called after every successful recomposition")
        }
    }


    @Composable
    private fun DisposableEffectDemo() {
        // this one is used when we need to free memory or items whenever
        //the composable leaves the composition
        // in this case we free the lifecycle observer, but also callbacks can be managed in here
        val lifecycleOwner = LocalLifecycleOwner.current

        DisposableEffect(key1 = lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_PAUSE) {
                    println("On pause called")
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }

    @Composable
    fun WithLaunchedEffect(viewModel: LaunchEffectViewModel) {
        var textState by remember {
            mutableStateOf("")
        }
        val scaffoldState = rememberScaffoldState()

        // is a composable
        // when the key changes the coroutine scope (it) embedded into the scope is cancelled and relaunched
        LaunchedEffect(key1 = textState) {

        }

        // this key activation only happens once because the key does not changes
        // and will only be executed again when the whole compose function is called again
        LaunchedEffect(key1 = true) {
            viewModel.sharedFlow.collectLatest { event ->
                when (event) {
                    is LaunchEffectViewModel.ScreenEvents.Navigate -> {}
                    is LaunchEffectViewModel.ScreenEvents.ShowSnackBar -> {
                        scaffoldState.snackbarHostState.showSnackbar(event.message)
                    }
                }
            }
        }

        Scaffold(modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Button(onClick = { textState += " " }) {
                    Text(text = "Click me")
                }
            }

        }

    }
}