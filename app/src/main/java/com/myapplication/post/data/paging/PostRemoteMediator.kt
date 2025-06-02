package com.myapplication.post.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.myapplication.post.data.local.database.PostDatabase
import com.myapplication.post.data.local.entity.PostEntity
import com.myapplication.post.data.mapper.toEntity
import com.myapplication.post.data.remote.api.PostApiService

/**
 * RemoteMediator implementation that acts as the single source of truth for posts.
 * It fetches data from the remote API, merges it with any locally stored favorites and comments,
 * and then updates the local database in a single transaction to ensure consistency.
 */

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val api: PostApiService,
    private val db: PostDatabase
) : RemoteMediator<Int, PostEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>
    ): MediatorResult {
        return try {
            if (loadType == LoadType.REFRESH) {
                val localPosts = db.postDao().getRemotePosts()
                val remotePosts = api.getPosts()

                val localPostsMap = localPosts.associateBy { it.id }
                val mergedPosts = remotePosts.map { remotePost ->
                    localPostsMap[remotePost.id]?.let { localPost ->
                        remotePost.toEntity(
                            isFavorite = localPost.isFavorite,
                            comment = localPost.comment
                        )
                    } ?: remotePost.toEntity()
                }

                db.withTransaction {
                    db.postDao().run {
                        clearRemoteCachedPosts()
                        upsertPosts(*mergedPosts.toTypedArray())
                    }
                }

            }

            MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
