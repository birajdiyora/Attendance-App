package com.example.attendance

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@HiltAndroidApp
class AttendanceApplication() : Application(){

}

class UserManager(private val context: Context){

    companion object{
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("studentToken")
        private val STUDENT_LOGGEDIN_KEY = booleanPreferencesKey("student_loggedIn")
    }

    suspend fun storeStudentLoggedInOrNot(loggedIn : Boolean) {
       context.dataStore.edit {
            it[STUDENT_LOGGEDIN_KEY] = loggedIn
        }
    }

    val studentLoggedInOrNot : Flow<Boolean> = context.dataStore.data.map {
        it[STUDENT_LOGGEDIN_KEY] ?: false
    }
}