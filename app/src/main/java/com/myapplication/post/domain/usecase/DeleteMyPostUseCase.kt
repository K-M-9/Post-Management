package com.myapplication.post.domain.usecase

import com.myapplication.post.domain.repository.PostRepository
import jakarta.inject.Inject

class DeleteMyPostUseCase @Inject constructor(
    private val repository: PostRepository
) {

    suspend operator fun invoke(id: Int) {
        repository.deleteMyPost(id)
    }

}