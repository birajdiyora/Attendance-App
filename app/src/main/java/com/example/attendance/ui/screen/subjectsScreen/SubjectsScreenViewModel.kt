package com.example.attendance.ui.screen.subjectsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.data.AttendanceRepository
import com.example.attendance.data.Subject
import com.example.attendance.util.SubjectListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubjectsScreenViewModel @Inject constructor(
    val repository: AttendanceRepository
) : ViewModel() {
    var addSubjectEditTextState by mutableStateOf("")
    var addSubjectDialogShow by mutableStateOf(false)
        private set
    var addSubjectDialogTextErrorText by mutableStateOf("")
        private set
    var editSubjectNameDialogShow by mutableStateOf(false)
        private set
    var editSubjectNameDialogEditText by mutableStateOf("")
    var currentSubjectOfEditDialog by mutableStateOf(Subject())

    private val _subjectListState = MutableStateFlow(SubjectListState())
    val subjectListState = _subjectListState.asStateFlow()

    init {
        viewModelScope.launch {
            getSubjects()
        }
    }

    fun onAddSubjectEditTextChange(value : String) {
        addSubjectEditTextState = value
    }
    fun addSubjectDialogShowStateChange(show : Boolean){
        addSubjectDialogShow = show
    }
    fun addSubject(subject: Subject) {
        if(addSubjectEditTextState != ""){
            viewModelScope.launch {
                repository.insertSubject(subject)
            }
            addSubjectDialogShowStateChange(false)
            addSubjectEditTextState = ""
        }else{
            addSubjectDialogTextErrorText = "Please enter subject name"
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

    fun editSubjectNameDialogShowStateChange(show : Boolean,subject: Subject){
        editSubjectNameDialogShow = show
        if(editSubjectNameDialogShow){
            currentSubjectOfEditDialog = subject
        }
    }
    fun onEditSubjectNameEditTextChange(value : String) {
        editSubjectNameDialogEditText = value
    }
    fun updateSubject(subjectId : Int) {
        if(editSubjectNameDialogEditText != ""){
            viewModelScope.launch {
                repository.updateSubjectById(subjectId = subjectId, subjectName = editSubjectNameDialogEditText)
            }
            editSubjectNameDialogShowStateChange(false,Subject())
            editSubjectNameDialogEditText = ""
        }
    }
}