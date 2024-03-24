package com.example.attendance.di

import android.content.Context
import androidx.room.Room
import com.example.attendance.UserManager
import com.example.attendance.data.AttendanceDatabase
import com.example.attendance.data.AttendanceRepository
import com.example.attendance.data.OfflineAttendanceRepository
import com.example.attendance.data.StudentDao
import com.example.attendance.data.SubjectAttendanceDao
import com.example.attendance.data.SubjectDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun getStudentDao(attendanceDatabase: AttendanceDatabase) : StudentDao = attendanceDatabase.getStudentDao()

    @Singleton
    @Provides
    fun getSubjectDao(attendanceDatabase: AttendanceDatabase) : SubjectDao = attendanceDatabase.getSubjectDao()

    @Singleton
    @Provides
    fun getSubjectAttendanceDao(attendanceDatabase: AttendanceDatabase) : SubjectAttendanceDao = attendanceDatabase.getSubjectAttendanceDao()

    @Singleton
    @Provides
    fun getAttendanceDatabase(@ApplicationContext context: Context) : AttendanceDatabase =
        Room.databaseBuilder(context,AttendanceDatabase::class.java,"Attendance_Database")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun getAttendanceRepository(
        subjectDao: SubjectDao,
        subjectAttendanceDao: SubjectAttendanceDao,
        studentDao: StudentDao) : AttendanceRepository  {
        return OfflineAttendanceRepository(
            subjectDao = subjectDao,
            subjectAttendanceDao = subjectAttendanceDao,
            studentDao = studentDao
        )
    }

    @Singleton
    @Provides
    fun getUserManager(@ApplicationContext context: Context) : UserManager = UserManager(context = context)

}