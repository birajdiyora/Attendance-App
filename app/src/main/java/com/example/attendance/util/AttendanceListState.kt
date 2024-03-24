package com.example.attendance.util

import com.example.attendance.data.AttendanceItem

data class AttendanceListState(
    val attendanceList : List<AttendanceItem> = listOf()
)
