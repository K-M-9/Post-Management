package com.myapplication.post.data.remote.dto

data class PostDto(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)