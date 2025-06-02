package com.myapplication.post.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.myapplication.post.data.local.database.PostDatabase
import com.myapplication.post.data.mapper.toDomain
import com.myapplication.post.data.mapper.toEntity
import com.myapplication.post.data.paging.PostRemoteMediator
import com.myapplication.post.data.remote.api.PostApiService
import com.myapplication.post.domain.model.Post
import com.myapplication.post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val db: PostDatabase,
    private val api: PostApiService
) : PostRepository {

    private fun createPagingConfig() = PagingConfig(
        pageSize = 20,
        initialLoadSize = 30,
        prefetchDistance = 5,
        enablePlaceholders = false
    )

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = createPagingConfig(),
            remoteMediator = PostRemoteMediator(api, db),
            pagingSourceFactory = { db.postDao().getAllPosts() }
        ).flow.map { pagingData -> pagingData.map { entity -> entity.toDomain() } }
    }

    override fun getFavoritePosts(): Flow<PagingData<Post>> {
        return Pager(
            config = createPagingConfig(),
            pagingSourceFactory = { db.postDao().getFavoritePosts() }
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }

    override suspend fun savePost(post: Post) {
        db.postDao().upsertPosts(post.toEntity())
    }

    override suspend fun togglePostFavorite(id: Int) {
        db.postDao().togglePostFavorite(id)
    }

    override suspend fun deleteMyPost(id: Int) {
        db.postDao().deleteMyPostById(id)
    }

    override fun getMyPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = createPagingConfig(),
            pagingSourceFactory = { db.postDao().getLocallyCreatedPosts() }
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }

    override fun searchPostsByTitle(query: String): Flow<PagingData<Post>> {
        return Pager(
            config = createPagingConfig(),
            pagingSourceFactory = { db.postDao().searchPostsByTitle(query) }
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }

    override suspend fun updatePostComment(id: Int, comment: String?) {
        db.postDao().updatePostComment(id, comment)
    }
}