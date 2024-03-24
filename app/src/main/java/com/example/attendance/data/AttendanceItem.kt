package com.example.attendance.data

data class AttendanceItem (
    val attendanceId : Int = 0,
    val subjectId : Int = 0,
    val subjectName : String = "",
    val attendance : Int = 0,
    val date : String = ""
)