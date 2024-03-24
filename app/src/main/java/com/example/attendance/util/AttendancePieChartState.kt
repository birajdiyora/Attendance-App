package com.example.attendance.util

data class AttendancePieChartState(
    val overallPercentage : Double = 0.0,
    val totalPresentLecture : Int = 0,
    val totalAbsentLecture : Int = 0,
    val totalLecture : Int = 0
)