package com.myapplication.post.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.myapplication.post.data.local.entity.PostEntity
import com.myapplication.post.data.local.entity.PostState

@Dao
interface PostDao {

    @Query("SELECT * FROM posts WHERE title LIKE '%' || :query || '%' ORDER BY id DESC")
    fun searchPostsByTitle(query: String): PagingSource<Int, PostEntity>

    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAllPosts(): PagingSource<Int, PostEntity>

    @Query("SELECT * FROM posts WHERE isFavorite = 1 ORDER BY id DESC")
    fun getFavoritePosts(): PagingSource<Int, PostEntity>

    @Query("SELECT * FROM posts WHERE isMyPost = 1 ORDER BY id DESC")
    fun getLocallyCreatedPosts(): PagingSource<Int, PostEntity>

    @Query("SELECT id, comment, isFavorite FROM posts WHERE isMyPost = 0")
    suspend fun getRemotePosts(): List<PostState>

    @Upsert
    suspend fun upsertPosts(vararg post: PostEntity)

    @Query("UPDATE posts SET comment = :comment WHERE id = :id")
    suspend fun updatePostComment(id: Int, comment: String?)

    @Query("UPDATE posts SET isFavorite = NOT isFavorite WHERE id = :id")
    suspend fun togglePostFavorite(id: Int)

    @Query("DELETE FROM posts WHERE id = :id AND isMyPost=1")
    suspend fun deleteMyPostById(id: Int)

    @Query("DELETE FROM posts WHERE isMyPost = 0 AND isFavorite = 0")
    suspend fun clearRemoteCachedPosts()

}