package com.myapplication.post.domain.usecase

import androidx.paging.PagingData
import com.myapplication.post.domain.model.Post
import com.myapplication.post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(): Flow<PagingData<Post>> = repository.getMyPosts()
}