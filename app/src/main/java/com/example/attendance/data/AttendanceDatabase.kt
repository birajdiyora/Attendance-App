package com.example.attendance.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Attendance database
@Database(entities = [Subject::class,SubjectAttendance::class,Student::class], version = 1, exportSchema = false)
abstract class AttendanceDatabase : RoomDatabase() {
    abstract fun getStudentDao() : StudentDao
    abstract fun getSubjectDao() : SubjectDao
    abstract fun getSubjectAttendanceDao() : SubjectAttendanceDao

    companion object{
        @Volatile
        private var instance : AttendanceDatabase? = null
        fun getDatabase(context: Context) : AttendanceDatabase{
            return instance ?: synchronized(this){
                Room.databaseBuilder(context,AttendanceDatabase::class.java,"Attendance_Database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}