package com.myapplication.post.domain.usecase

import androidx.paging.PagingData
import com.myapplication.post.domain.model.Post
import com.myapplication.post.domain.repository.PostRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetFavoritePostsUseCase @Inject constructor(
    private val repository: PostRepository
) {

     operator fun invoke(): Flow<PagingData<Post>>  = repository.getFavoritePosts()
}