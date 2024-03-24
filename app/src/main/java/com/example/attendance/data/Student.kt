package com.example.attendance.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String = "",
    val totalPresentLecture : Int = 0,
    val totalAbsentLecture : Int = 0
)
