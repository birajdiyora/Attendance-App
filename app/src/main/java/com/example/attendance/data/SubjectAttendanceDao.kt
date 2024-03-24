package com.example.attendance.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectAttendanceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAttendance(subjectAttendance: SubjectAttendance)

    @Query("DELETE from subject_attendance where subjectAttendanceId = :attendanceId")
    suspend fun deleteAttendanceByAttendanceId(attendanceId : Int)

    @Query("SELECT COUNT(subjectAttendanceId) from subject_attendance where subjectId = :subjectId and date = :date")
    suspend fun getNoOfAttendanceOfSameSubjectSameDate(subjectId : Int,date : String) : Int

    @Query("SELECT * FROM subject_attendance where subjectId = :subjectId ORDER BY date desc,subjectAttendanceId desc ")
    fun getAttendanceBySubjectId(subjectId: Int) : Flow<List<SubjectAttendance>>

    @Query("SELECT * FROM subject_attendance ORDER BY date desc,subjectAttendanceId desc")
    fun getAttendance() : Flow<List<SubjectAttendance>>
}