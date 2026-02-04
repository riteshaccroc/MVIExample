package com.example.mviexample.domain.usecase.datastore

import androidx.datastore.preferences.core.Preferences
import com.example.mviexample.domain.repository.DataStoreRepository
import javax.inject.Inject

class SavePreferenceUseCase @Inject constructor(
    private val repo: DataStoreRepository
) {
    suspend operator fun invoke(key: Preferences.Key<Boolean>, value: Boolean) {
        repo.savePref(key, value)
    }
}