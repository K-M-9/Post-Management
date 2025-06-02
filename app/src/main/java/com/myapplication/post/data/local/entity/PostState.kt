package com.myapplication.post.data.local.entity

data class PostState(
    val id: Int,
    val isFavorite: Boolean = false,
    val comment: String? = null
)
