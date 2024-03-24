package com.example.attendance.ui.screen.addNameScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.AttendanceApplication
import com.example.attendance.MainActivity
import com.example.attendance.data.AttendanceRepository
import com.example.attendance.data.Student
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNameScreenViewModel @Inject constructor(
    val repository: AttendanceRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var fullNameTextFieldState = mutableStateOf("")
        private set

    var fullNameTextFiledErrorState = mutableStateOf("")
        private set

    fun fullNameTextFieldValueChange(fullName : String){
        fullNameTextFieldState.value = fullName
    }

     fun onSubmitButtonClick(){
        if(fullNameTextFieldState.value != ""){
            fullNameTextFiledErrorState.value = ""
            viewModelScope.launch {
                repository.insertStudent(student = Student(name = fullNameTextFieldState.value))
            }
            viewModelScope.launch {
                MainActivity.userManager.storeStudentLoggedInOrNot(true)
            }
        }else{
            fullNameTextFiledErrorState.value = "Please enter name"
        }
    }
}