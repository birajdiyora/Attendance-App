package com.example.attendance.navigation

sealed class AttendanceScreen(
    val tittle : String,
    val route : String
){
    object HomeScreen : AttendanceScreen(tittle = "Home", route = "home_screen")
    object AddNameScreen : AttendanceScreen(tittle = "Add Name", route = "add_name_screen")
    object SubjectScreen : AttendanceScreen(tittle = "Subject", route = "subject_screen")
    object ViewAttendanceScreen : AttendanceScreen(tittle = "View Attendance", route = "view_attendance_screen")
    object ViewSubjectAttendanceScreen : AttendanceScreen(tittle = "View Subject Attendance", route = "view_subject_attendance_screen")
    object AboutUsScreen : AttendanceScreen(tittle = "About Us", route = "about_us")
}
