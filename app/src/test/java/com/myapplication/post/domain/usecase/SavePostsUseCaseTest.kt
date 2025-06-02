package com.myapplication.post.domain.usecase

import com.myapplication.post.domain.model.Post
import com.myapplication.post.domain.repository.PostRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test


class SavePostsUseCaseTest {
    private val repository = mockk<PostRepository>(relaxed = true)
    private val useCase = SavePostsUseCase(repository)

    @Test
    fun `GIVEN a post WHEN invoke is called THEN repository savePost is called`() = runTest {
        // Given
        val post = Post(
            id = 1,
            title = "Test Post",
            body = "Test Body",
            userId = -1,
            isMyPost = true,
            comment = "Test Content"
        )
        coEvery { repository.savePost(post) } returns Unit

        // When
        useCase(post)

        // Then
        coVerify(exactly = 1) { repository.savePost(post) }
    }
}
