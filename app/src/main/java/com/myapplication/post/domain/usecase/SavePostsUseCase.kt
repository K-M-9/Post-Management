package com.myapplication.post.domain.usecase

import com.myapplication.post.domain.model.Post
import com.myapplication.post.domain.repository.PostRepository
import javax.inject.Inject

class SavePostsUseCase @Inject constructor(
    private val repository: PostRepository
){
    suspend  operator fun invoke( post: Post){
        repository.savePost(post)
    }
}