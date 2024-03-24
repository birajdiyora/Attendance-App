package com.example.attendance.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudent(student: Student)

    @Query("SELECT * from student")
    fun getStudent() : Flow<List<Student>>

    @Query("SELECT COUNT(ID) from student")
    suspend fun getNoOfStudent() : Int

    @Query("UPDATE student set totalPresentLecture = :presentLecture where id = 1")
    suspend fun updatePresentLecture(presentLecture : Int)

    @Query("UPDATE student set totalAbsentLecture = :absentLecture where id = 1")
    suspend fun updateAbsentLecture(absentLecture : Int)

    @Query("SELECT * FROM student where id = 1")
    fun getStudentById1() : Flow<Student>

    @Query("UPDATE student set totalPresentLecture = (totalPresentLecture - :presentLecture),totalAbsentLecture = (totalAbsentLecture - :absentLecture) where id = 1")
    suspend fun updateLectureCountAfterDeleteSubject(presentLecture: Int,absentLecture: Int)

}