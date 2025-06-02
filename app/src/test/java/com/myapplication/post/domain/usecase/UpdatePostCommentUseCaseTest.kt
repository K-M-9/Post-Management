package com.myapplication.post.domain.usecase

import com.myapplication.post.domain.repository.PostRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UpdatePostCommentUseCaseTest {

    private val repository = mockk<PostRepository>(relaxed = true)
    private val useCase = UpdatePostCommentUseCase(repository)

    @Test
    fun `GIVEN post ID and comment WHEN invoke is called THEN repository updatePostComment is called`() =
        runTest {
            // Given
            val postId = 789
            val comment = "This is a test comment"
            coEvery { repository.updatePostComment(postId, comment) } returns Unit

            // When
            useCase(postId, comment)

            // Then
            coVerify(exactly = 1) { repository.updatePostComment(postId, comment) }
        }
}