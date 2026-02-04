package com.example.mviexample.domain.usecase.datastore

import androidx.datastore.preferences.core.Preferences
import com.example.mviexample.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPreferenceUseCase @Inject constructor(
    private val repo: DataStoreRepository
) {
    operator fun invoke(key: Preferences.Key<Boolean>): Flow<Boolean> = repo.readPref(key)
}