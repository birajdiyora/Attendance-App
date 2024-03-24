package com.example.attendance.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class OfflineAttendanceRepository @Inject constructor(
    private val subjectDao : SubjectDao,
    private val subjectAttendanceDao: SubjectAttendanceDao,
    private val studentDao: StudentDao
) : AttendanceRepository{

    //student table
    override suspend fun insertStudent(student: Student) {
        studentDao.insertStudent(student = student)
    }

    override fun getStudent(): Flow<List<Student>> {
        return studentDao.getStudent()
    }

    override suspend fun getNoOfStudent(): Int {
        return studentDao.getNoOfStudent()
    }

    override suspend fun updatePresentLecture(presentLecture: Int) {
        studentDao.updatePresentLecture(presentLecture)
    }

    override suspend fun updateAbsentLecture(absentLecture: Int) {
        studentDao.updateAbsentLecture(absentLecture)
    }

    override fun getStudentById1(): Flow<Student> {
        return studentDao.getStudentById1()
    }

    override suspend fun updateLectureCountAfterDeleteSubject(
        presentLecture: Int,
        absentLecture: Int
    ) {
        studentDao.updateLectureCountAfterDeleteSubject(presentLecture = presentLecture, absentLecture = absentLecture)
    }

    //subject table
    override suspend fun insertSubject(subject: Subject) {
        subjectDao.insertSubject(subject)
    }

    override suspend fun updateSubjectById(subjectId : Int, subjecName : String) {
        subjectDao.updateSubjectBySubjectId(subjectId,subjecName)
    }

    override suspend fun deleteSubject(subject: Subject) {
        subjectDao.deleteSubject(subject)
    }

    override suspend fun deleteSubjectBySubjectId(subjectId: Int) {
        subjectDao.deleteSubjectBySubjectId(subjectId)
    }

    override fun getSubjectList(): Flow<List<Subject>> {
        return subjectDao.getSubjectList()
    }

    override fun getSubjectById(subjectId: Int): Flow<Subject> {
        return subjectDao.getSubjectById(subjectId)
    }

    override fun getSubjectNameBySubjectId(subjectId: Int): Flow<String> {
        return subjectDao.getSubjectNameBySubjectId(subjectId = subjectId)
    }

    override suspend fun updatePresentLectureCountIncrease(subjectId: Int) {
       subjectDao.updatePresentLectureCountIncrease(subjectId)
    }

    override suspend fun updateAbsentLectureCountIncrease(subjectId: Int) {
        subjectDao.updateAbsentLectureCountIncrease(subjectId = subjectId)
    }

    override suspend fun updatePresentLectureCountDecrease(subjectId: Int) {
        subjectDao.updatePresentLectureCountDecrease(subjectId)
    }

    override suspend fun updateAbsentLectureCountDecrease(subjectId: Int) {
        subjectDao.updateAbsentLectureCountDecrease(subjectId)
    }

    override fun getTotalNoOfPresentLecture(): Flow<Int> {
        return subjectDao.getTotalNoOfPresentLecture()
    }

    override fun getTotalNoOfAbsentLecture(): Flow<Int> {
        return subjectDao.getTotalNoOfAbsentLecture()
    }

    override fun getAttendance(): Flow<List<SubjectAttendance>> {
        return subjectAttendanceDao.getAttendance()
    }

    //subject_attendance table

    override suspend fun insertAttendance(subjectAttendance: SubjectAttendance) {
        subjectAttendanceDao.insertAttendance(subjectAttendance)
    }

    override suspend fun deleteAttendanceByAttendanceId(attendanceId: Int) {
        subjectAttendanceDao.deleteAttendanceByAttendanceId(attendanceId)
    }

    override suspend fun getNoOfAttendanceOfSameSubjectSameDate(subjectId: Int, date: String): Int {
        return subjectAttendanceDao.getNoOfAttendanceOfSameSubjectSameDate(subjectId = subjectId, date = date)
    }

    override fun getAttendanceBySubjectId(subjectId: Int): Flow<List<SubjectAttendance>> {
        return subjectAttendanceDao.getAttendanceBySubjectId(subjectId = subjectId)
    }

}