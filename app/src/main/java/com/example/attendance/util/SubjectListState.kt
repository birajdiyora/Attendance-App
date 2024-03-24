package com.example.attendance.util

import com.example.attendance.data.Subject

data class SubjectListState(
    val subjectList: List<Subject> = listOf(Subject(1,"Java",25,30))
)
