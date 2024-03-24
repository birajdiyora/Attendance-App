package com.example.attendance

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.MainActivity
import com.example.attendance.navigation.AttendanceScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    var startingScreen = mutableStateOf(AttendanceScreen.AddNameScreen.route)
        private set
    var splashCondition = mutableStateOf(true)
        private set

    init {
        viewModelScope.launch {
            MainActivity.userManager.studentLoggedInOrNot.collect { studentLoggedIn ->
                if (studentLoggedIn) {
//                    Log.d("test", startingScreen.value)
                    startingScreen.value = AttendanceScreen.HomeScreen.route
                } else {
//                    Log.d("test", startingScreen.value)
                    startingScreen.value = AttendanceScreen.AddNameScreen.route
                }
                delay(300)
                splashCondition.value = false
            }
        }
    }
}