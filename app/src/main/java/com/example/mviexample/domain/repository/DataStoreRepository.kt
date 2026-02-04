package com.example.mviexample.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun setFirstTimeLaunched(isFirstTime: Boolean)
    fun isFirstTimeLaunch(): Flow<Boolean>

    suspend fun savePref(key: Preferences.Key<Boolean>, value: Boolean)

    fun readPref(key: Preferences.Key<Boolean>): Flow<Boolean>
}