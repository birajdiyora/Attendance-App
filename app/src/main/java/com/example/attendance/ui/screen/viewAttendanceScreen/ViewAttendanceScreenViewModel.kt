package com.example.attendance.ui.screen.viewAttendanceScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.convertDateInMillisToDate
import com.example.attendance.data.AttendanceItem
import com.example.attendance.data.AttendanceRepository
import com.example.attendance.data.SubjectAttendance
import com.example.attendance.findPercentageOfAttendance
import com.example.attendance.updateTotalAbsentLecture
import com.example.attendance.updateTotalPresentLecture
import com.example.attendance.util.AttendanceListState
import com.example.attendance.util.AttendancePieChartState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewAttendanceScreenViewModel @Inject constructor(
    val repository: AttendanceRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var deleteAttendanceDialogStateShow by mutableStateOf(false)
        private set
    var deleteAttendanceItemData by mutableStateOf(AttendanceItem())

    var _attendanceListState  = MutableStateFlow(AttendanceListState())
    val  attendanceListState : StateFlow<AttendanceListState> = _attendanceListState.asStateFlow()

    var _attendancePieChartState  = MutableStateFlow(AttendancePieChartState())
    val  attendancePieChartState : StateFlow<AttendancePieChartState> = _attendancePieChartState.asStateFlow()

    init {
        viewModelScope.launch {
            getAttendanceData()
        }
        viewModelScope.launch {
            getListOfAttendance()
        }
    }

    private suspend fun getAttendanceData() {
        repository.getStudentById1().collect { student ->
            _attendancePieChartState.update { attendancePieChartState ->
                attendancePieChartState.copy(
                    overallPercentage = findPercentageOfAttendance(
                        presentLecture = student.totalPresentLecture,
                        absentLecture = student.totalAbsentLecture
                    ),
                    totalPresentLecture = student.totalPresentLecture,
                    totalAbsentLecture = student.totalAbsentLecture,
                    totalLecture = student.totalPresentLecture + student.totalAbsentLecture
                )
            }
        }
    }
   private suspend fun getListOfAttendance() {
        repository.getAttendance().collect{attendanceItemList ->
            _attendanceListState.update {
                it.copy(
                    attendanceList = attendanceItemList.map {
                        it.toAttendanceItem()
                    }
                )
            }
        }
    }
    fun onDeleteAttendance() {
        viewModelScope.launch {
            repository.deleteAttendanceByAttendanceId(deleteAttendanceItemData.attendanceId)
        }
        viewModelScope.launch {
            if(deleteAttendanceItemData.attendance == 1){
                repository.updatePresentLectureCountDecrease(deleteAttendanceItemData.subjectId)
                updateTotalPresentLecture(repository)
            }else{
                repository.updateAbsentLectureCountDecrease(deleteAttendanceItemData.subjectId)
                updateTotalAbsentLecture(repository)
            }
        }
        onDeleteAttendanceDialogStateChange(false)
    }
    fun setValueDeleteAttendanceItemData(attendanceItem: AttendanceItem) {
        deleteAttendanceItemData = attendanceItem
    }
    fun onDeleteAttendanceDialogStateChange(isShow : Boolean) {
        deleteAttendanceDialogStateShow = isShow
    }
    private fun SubjectAttendance.toAttendanceItem() = AttendanceItem(
        attendanceId = subjectAttendanceId,
        subjectName = subjectName,
        attendance = attendance,
        date = convertDateInMillisToDate(date),
        subjectId = subjectId
    )
    private fun findSubjectNameBySubjectId(subjectId : Int) : String{
        var subjectName : String = ""
        viewModelScope.launch {
            repository.getSubjectNameBySubjectId(subjectId).collect{
                subjectName = it
            }
        }
        return subjectName
    }
}