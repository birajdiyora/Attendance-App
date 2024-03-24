package com.example.attendance.ui.screen.viewSubjectAttendanceScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.convertDateInMillisToDate
import com.example.attendance.data.AttendanceRepository
import com.example.attendance.data.AttendanceSubjectItem
import com.example.attendance.data.Subject
import com.example.attendance.data.SubjectAttendance
import com.example.attendance.findPercentageOfAttendance
import com.example.attendance.updateTotalAbsentLecture
import com.example.attendance.updateTotalPresentLecture
import com.example.attendance.util.SubjectAttendanceAddDialogState
import com.example.attendance.util.SubjectAttendanceListState
import com.example.attendance.util.SubjectAttendancePieChartState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ViewSubjectAttendanceScreenViewModel @Inject constructor(
    val repository: AttendanceRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val subjectId : Int = checkNotNull(savedStateHandle["subject_id"])
    val subjectName : String = checkNotNull(savedStateHandle["subject_name"])

    var topBarSubjectNameState by mutableStateOf("")
    var deleteSubjectAttendanceDialogStateShow by mutableStateOf(false)
    var deleteAttendanceDataState by mutableStateOf(AttendanceSubjectItem())
    var deleteSubjectDialogStateShow by mutableStateOf(false)

    private var _addAttendanceDialogState = MutableStateFlow(SubjectAttendanceAddDialogState())
    val addAttendanceDialogState : StateFlow<SubjectAttendanceAddDialogState> = _addAttendanceDialogState

    private var _subjectAttendancePieChartState = MutableStateFlow(SubjectAttendancePieChartState())
    val subjectAttendancePieChartState : StateFlow<SubjectAttendancePieChartState> = _subjectAttendancePieChartState

    private var _subjectAttendanceListState = MutableStateFlow(SubjectAttendanceListState())
    val subjectAttendanceListState : StateFlow<SubjectAttendanceListState> = _subjectAttendanceListState

    init {
        topBarSubjectNameState = subjectName
        getSubjectAttendanceData()
    }

    fun onSubjectAttendanceDialogShow(show : Boolean) {
        viewModelScope.launch {
            _addAttendanceDialogState.update{
                it.copy(
                    subjectName = subjectName,
                    show = show
                )
            }
        }
    }

    fun addAttendance(attendance : Int,date : Long) {
            viewModelScope.launch {
                repository.insertAttendance(
                    SubjectAttendance(
                        subjectId = subjectId,
                        attendance = attendance,
                        date = date,
                        subjectName = subjectName
                    )
                )
                _addAttendanceDialogState.update{
                    it.copy(
                        subjectName = subjectName,
                        show = false
                    )
                }
            }
        if(attendance == 0){
            onUpdateSubjectAbsentIncreaseLectureData()
        }else{
            onUpdateSubjectPresentIncreaseLectureData()
        }
    }

    fun getSubjectAttendanceData() {
            viewModelScope.launch {
                repository.getSubjectById(subjectId = subjectId).collect{subject ->
                    if(subject != null){
                        _subjectAttendancePieChartState.value = subject.toSubjectAttendancePieChartState()
                    }
                }
            }
        viewModelScope.launch {
            repository.getAttendanceBySubjectId(subjectId = subjectId).collect{subjectAttendance ->
                _subjectAttendanceListState.update {
                    it.copy(
                        subjectAttendanceList = subjectAttendance.map {
                            it.toAttendanceSubjectItem()
                        }
                    )
                }
            }
        }
    }

    fun onUpdateSubjectPresentIncreaseLectureData() {
        viewModelScope.launch {
            repository.updatePresentLectureCountIncrease(subjectId)
            updateTotalPresentLecture(repository)
        }
    }

    fun onUpdateSubjectAbsentIncreaseLectureData() {
        viewModelScope.launch {
            repository.updateAbsentLectureCountIncrease(subjectId)
            updateTotalAbsentLecture(repository)
        }
    }

    fun onDeleteAttendance(){
        viewModelScope.launch {
            repository.deleteAttendanceByAttendanceId(attendanceId = deleteAttendanceDataState.attendanceId)
        }
        viewModelScope.launch {
            if(deleteAttendanceDataState.attendance.equals("Present",true)){
                repository.updatePresentLectureCountDecrease(subjectId)
                updateTotalPresentLecture(repository)
            }else{
                repository.updateAbsentLectureCountDecrease(subjectId)
                updateTotalAbsentLecture(repository)
            }
        }
    }

    fun onSubjectDeleteButtonClick() {
        viewModelScope.launch {
            repository.getSubjectById(subjectId).collect{
                if(it != null) {
                    repository.updateLectureCountAfterDeleteSubject(
                        presentLecture = it.presentLecture,
                        absentLecture = it.absentLecture
                    )
                }
                }
        }
        viewModelScope.launch {
            repository.deleteSubjectBySubjectId(subjectId)
        }
    }

    fun checkAttendanceValid(subjectId : Int,date: String) : Boolean {
        var validOrNot : Boolean = false
        viewModelScope.launch {
            val count = repository.getNoOfAttendanceOfSameSubjectSameDate(subjectId = subjectId, date = date)
            validOrNot = count == 0
        }
        return validOrNot
    }
}


fun Subject.toSubjectAttendancePieChartState() : SubjectAttendancePieChartState {
    val per = findPercentageOfAttendance(presentLecture,absentLecture)
   return SubjectAttendancePieChartState(
        percentage = per,
        presentLecture = presentLecture,
        absentLecture = absentLecture,
        totalLecture = presentLecture + absentLecture
    )
}

fun SubjectAttendance.toAttendanceSubjectItem() = AttendanceSubjectItem(
    attendance = if(attendance == 0){"Absent"}else{"Present"},
    date = convertDateInMillisToDate(date),
    attendanceId = subjectAttendanceId
)
