package com.myapplication.post.data.mapper

import com.myapplication.post.data.local.entity.PostEntity
import com.myapplication.post.data.remote.dto.PostDto
import com.myapplication.post.domain.model.Post


fun PostDto.toDomain(): Post = Post(
    id = id,
    title = title,
    body = body,
    userId = userId,
    isMyPost = true,
    isFavorite = false,
    comment = null
)

fun Post.toDto(): PostDto = PostDto(
    id = id,
    title = title,
    body = body,
    userId = userId
)

fun PostEntity.toDomain(): Post = Post(
    id = id,
    title = title,
    body = body,
    userId = userId,
    isFavorite = isFavorite,
    comment = comment,
    isMyPost = isMyPost
)

fun Post.toEntity(): PostEntity = PostEntity(
    id = id,
    title = title,
    body = body,
    userId = userId,
    isFavorite = isFavorite,
    comment = comment,
    isMyPost = isMyPost
)

fun PostDto.toEntity(isFavorite: Boolean = false, comment: String? = null): PostEntity = PostEntity(
    id = id,
    title = title,
    body = body,
    userId = userId,
    isFavorite = isFavorite,
    comment = comment,
)