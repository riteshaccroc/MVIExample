package com.example.mviexample.data.repository

import androidx.datastore.preferences.core.Preferences
import com.example.mviexample.data.dataStore.DataStoreManager
import com.example.mviexample.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : DataStoreRepository {

    override suspend fun setFirstTimeLaunched(isFirstTime: Boolean) {
        dataStoreManager.setFirstTimeLaunched(isFirstTime)
    }

    override fun isFirstTimeLaunch(): Flow<Boolean> =
        dataStoreManager.isFirstTimeLaunch()

    override suspend fun savePref(
        key: Preferences.Key<Boolean>,
        value: Boolean
    ) {
        dataStoreManager.saveBoolean(key, value)
    }

    override fun readPref(key: Preferences.Key<Boolean>) =
        dataStoreManager.getBoolean(key)
}