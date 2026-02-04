package com.example.mviexample.di

import android.content.Context
import com.example.mviexample.data.dataStore.DataStoreManager
import com.example.mviexample.data.repository.DataStoreRepositoryImpl
import com.example.mviexample.domain.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)// lifecycle, Ensures this instance lives as long as the application
@Module
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(
        dataStoreManager: DataStoreManager
    ): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStoreManager)
    }
}
