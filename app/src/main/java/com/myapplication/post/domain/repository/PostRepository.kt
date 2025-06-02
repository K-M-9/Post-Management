package com.myapplication.post.domain.repository

import androidx.paging.PagingData
import com.myapplication.post.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun getAllPosts(): Flow<PagingData<Post>>

    fun getFavoritePosts(): Flow<PagingData<Post>>

    fun getMyPosts(): Flow<PagingData<Post>>

    fun searchPostsByTitle(query: String): Flow<PagingData<Post>>

    suspend fun savePost(post: Post)

    suspend fun togglePostFavorite(id: Int)

    suspend fun deleteMyPost(id: Int)

    suspend fun updatePostComment(id: Int, comment: String?)

}