package com.example.attendance.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSubject(subject: Subject)

    @Query("Update subject set subjectName = :subjectName where subjectId = :subjectId")
    suspend fun updateSubjectBySubjectId(subjectId : Int, subjectName : String)

    @Delete
    suspend fun deleteSubject(subject: Subject)

    @Query("DELETE from subject where subjectId = :subjectId")
    suspend fun deleteSubjectBySubjectId(subjectId: Int)

    @Query("SELECT * from subject")
    fun getSubjectList() : Flow<List<Subject>>

    @Query("SELECT * FROM subject where subjectId = :subjectId")
    fun getSubjectById(subjectId : Int) : Flow<Subject>

    @Query("SELECT subjectName from subject where subjectId = :subjectId")
    fun getSubjectNameBySubjectId(subjectId: Int) : Flow<String>

    @Query("UPDATE subject SET presentLecture = presentLecture + 1 WHERE subjectId = :subjectId")
    suspend fun updatePresentLectureCountIncrease(subjectId: Int)

    @Query("UPDATE subject SET absentLecture = absentLecture + 1 WHERE subjectId = :subjectId")
    suspend fun updateAbsentLectureCountIncrease(subjectId: Int)

    @Query("UPDATE subject SET presentLecture = presentLecture - 1 WHERE subjectId = :subjectId")
    suspend fun updatePresentLectureCountDecrease(subjectId: Int)

    @Query("UPDATE subject SET absentLecture = absentLecture - 1 WHERE subjectId = :subjectId")
    suspend fun updateAbsentLectureCountDecrease(subjectId: Int)

    @Query("SELECT SUM(presentLecture) FROM subject")
    fun getTotalNoOfPresentLecture() : Flow<Int>

    @Query("SELECT SUM(absentLecture) from subject")
    fun getTotalNoOfAbsentLecture() : Flow<Int>

}