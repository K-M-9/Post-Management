package com.myapplication.post.domain.usecase

import com.myapplication.post.domain.repository.PostRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test


class ToggleFavoriteUseCaseTest {

    private val repository = mockk<PostRepository>(relaxed = true)
    private val useCase = ToggleFavoriteUseCase(repository)

    @Test
    fun `GIVEN a post ID WHEN invoke is called THEN repository togglePostFavorite is called`() =
        runTest {
            // Given
            val postId = 123
            coEvery { repository.togglePostFavorite(postId) } returns Unit

            // When
            useCase(postId)

            // Then
            coVerify(exactly = 1) { repository.togglePostFavorite(postId) }
        }
}