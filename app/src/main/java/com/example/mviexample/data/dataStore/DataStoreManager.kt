package com.example.mviexample.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val DATASTORE_NAME = "datastoreCos"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

class DataStoreManager @Inject constructor(private val context: Context) {

    companion object {
        //Add keys here
        val IS_FIRST_TIME_KEY = booleanPreferencesKey("is_first_time")
    }

    /*// Save String
    suspend fun saveString(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    // Get String Flow
    fun getString(key: Preferences.Key<String>, defaultValue: String = ""): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }

    // Save Int
    suspend fun saveInt(key: Preferences.Key<Int>, value: Int) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    // Get Int Flow
    fun getInt(key: Preferences.Key<Int>, defaultValue: Int = 0): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }*/

    // Save Boolean
    suspend fun saveBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    fun getBoolean(key: Preferences.Key<Boolean>, defaultValue: Boolean = false): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }

    //Should there funcstions be in repo?, check
    // or do we even need them and just save/retrieve data from Repo
    suspend fun setFirstTimeLaunched(isFirstTime: Boolean) {
        saveBoolean(IS_FIRST_TIME_KEY, isFirstTime)
    }

    fun isFirstTimeLaunch(): Flow<Boolean> {
        return getBoolean(IS_FIRST_TIME_KEY, defaultValue = false)
    }

    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

/*    suspend fun <T> remove(key: Preferences.Key<T>) {
        context.dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }*/

    suspend fun remove(key: Preferences.Key<Boolean>) {
        context.dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }
}