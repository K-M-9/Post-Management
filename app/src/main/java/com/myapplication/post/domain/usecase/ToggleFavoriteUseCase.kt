package com.myapplication.post.domain.usecase

import com.myapplication.post.domain.repository.PostRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.togglePostFavorite(id)
    }
}