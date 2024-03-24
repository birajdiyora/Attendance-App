package com.example.attendance.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subject")
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val subjectId : Int = 0,
    val subjectName : String = "",
    val presentLecture : Int = 0,
    val absentLecture : Int = 0
)
