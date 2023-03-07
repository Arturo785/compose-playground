package com.example.android.composeplayground.states_and_effects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.composeplayground.states_and_effects.LaunchEffectViewModel.ScreenEvents.ShowSnackBar
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LaunchEffectViewModel : ViewModel() {


    private val _sharedFlow = Channel<ScreenEvents>()
    val sharedFlow = _sharedFlow.receiveAsFlow()


    init {
        viewModelScope.launch {
            _sharedFlow.send(ShowSnackBar("Hola mundo"))
        }
    }


    sealed class ScreenEvents {
        class ShowSnackBar(val message: String) : ScreenEvents()
        class Navigate(val route: String) : ScreenEvents()
    }
}