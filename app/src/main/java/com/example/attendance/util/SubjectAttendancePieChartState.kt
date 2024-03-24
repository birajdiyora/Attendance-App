package com.example.attendance.util

data class SubjectAttendancePieChartState(
    val percentage : Double = 0.0,
    val presentLecture : Int = 0,
    val absentLecture : Int = 0,
    val totalLecture : Int = 0
)
