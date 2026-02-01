package com.example.mviexample.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mviexample.domain.model.Post
import kotlinx.coroutines.flow.Flow


@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    fun getAllIPosts(): Flow<List<Post>>//TODO:check if it should be Post or PostDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(item: Post)//TODO:check if it should be Post or PostDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPosts(items: List<Post>)//TODO:check if it should be Post or PostDto

}