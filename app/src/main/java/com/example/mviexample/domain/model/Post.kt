package com.example.mviexample.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
) : Parcelable