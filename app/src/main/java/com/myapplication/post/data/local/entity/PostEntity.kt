package com.myapplication.post.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int,
    val isFavorite: Boolean = false,
    val comment: String? = null,
    val isMyPost: Boolean = false,
)
