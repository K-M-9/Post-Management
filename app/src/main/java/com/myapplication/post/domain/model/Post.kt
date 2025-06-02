package com.myapplication.post.domain.model

data class Post(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int,
    val isFavorite: Boolean = false,
    val comment: String?,
    val isMyPost: Boolean = false,
)