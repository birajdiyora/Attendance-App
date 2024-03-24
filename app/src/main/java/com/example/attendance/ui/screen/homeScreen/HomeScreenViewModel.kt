package com.example.attendance.ui.screen.homeScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.data.AttendanceRepository
import com.example.attendance.data.Student
import com.example.attendance.data.Subject
import com.example.attendance.util.StudentListState
import com.example.attendance.util.SubjectListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val repository: AttendanceRepository
) : ViewModel() {

    private var _studentList = MutableStateFlow(StudentListState())
    val studentList : StateFlow<StudentListState> = _studentList.asStateFlow()

    private val _subjectListState = MutableStateFlow(SubjectListState())
    val subjectListState = _subjectListState.asStateFlow()

    init {
        viewModelScope.launch {
            getSubjects()
        }
        viewModelScope.launch {
            repository.getStudent().collect{studentList ->
                _studentList.update {studentListState ->
                    studentListState.copy(
                        studentList = studentList
                    )
                }
            }
        }
    }
    suspend fun getSubjects() {
        repository.getSubjectList().collect{subjectList ->
            _subjectListState.update {
                it.copy(
                    subjectList = subjectList
                )
            }
        }
    }

}