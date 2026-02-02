package com.example.mviexample.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mviexample.data.remote.dto.PostDto

@Database(entities = [PostDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}