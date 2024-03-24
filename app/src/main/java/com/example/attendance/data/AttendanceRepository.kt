package com.example.attendance.data

import kotlinx.coroutines.flow.Flow


interface AttendanceRepository {

    //student table
    suspend fun insertStudent(student: Student)

    fun getStudent() : Flow<List<Student>>

    suspend fun getNoOfStudent() : Int

    suspend fun updatePresentLecture(presentLecture : Int)

    suspend fun updateAbsentLecture(absentLecture : Int)

    fun getStudentById1() : Flow<Student>

    suspend fun updateLectureCountAfterDeleteSubject(presentLecture: Int,absentLecture: Int)

    //subject table

    suspend fun insertSubject(subject: Subject)

    suspend fun updateSubjectById(subjectId : Int, subjectName : String)

    suspend fun deleteSubject(subject: Subject)

    suspend fun deleteSubjectBySubjectId(subjectId: Int)

    fun getSubjectList() : Flow<List<Subject>>

    fun getSubjectById(subjectId : Int) : Flow<Subject>

    fun getSubjectNameBySubjectId(subjectId: Int) : Flow<String>

    suspend fun updatePresentLectureCountIncrease(subjectId: Int)

    suspend fun updateAbsentLectureCountIncrease(subjectId: Int)

    suspend fun updatePresentLectureCountDecrease(subjectId: Int)

    suspend fun updateAbsentLectureCountDecrease(subjectId: Int)

    fun getTotalNoOfPresentLecture() : Flow<Int>

    fun getTotalNoOfAbsentLecture() : Flow<Int>

    fun getAttendance() : Flow<List<SubjectAttendance>>

    //subject_attendance table

    suspend fun insertAttendance(subjectAttendance: SubjectAttendance)

    suspend fun deleteAttendanceByAttendanceId(attendanceId : Int)

    suspend fun getNoOfAttendanceOfSameSubjectSameDate(subjectId : Int,date : String) : Int

    fun getAttendanceBySubjectId(subjectId: Int) : Flow<List<SubjectAttendance>>

}