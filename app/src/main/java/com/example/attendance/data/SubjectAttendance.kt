package com.example.attendance.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Subject::class,
            parentColumns = ["subjectId"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    tableName = "subject_attendance"
)
data class SubjectAttendance(
    @PrimaryKey(autoGenerate = true)
    val subjectAttendanceId : Int = 0,
    val subjectId : Int,
    val subjectName : String,
    val attendance : Int,
    val date : Long
)
