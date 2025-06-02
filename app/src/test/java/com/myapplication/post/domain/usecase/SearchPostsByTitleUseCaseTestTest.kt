package com.myapplication.post.domain.usecase

import androidx.paging.PagingData
import com.myapplication.post.domain.model.Post
import com.myapplication.post.domain.repository.PostRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class SearchPostsByTitleUseCaseTestTest {
    private val repository = mockk<PostRepository>(relaxed = true)
    private val useCase = SearchPostsByTitleUseCaseTest(repository)

    @Test
    fun `GIVEN repository returns search results flow WHEN invoke is called with query THEN return same flow`() =
        runTest {
            // Given
            val query = "test query"
            val expectedPagingData = PagingData.from(listOf<Post>())
            val expectedFlow = flowOf(expectedPagingData)
            every { repository.searchPostsByTitle(query) } returns expectedFlow

            // When
            val result = useCase(query)

            // Then
            assertEquals(expectedFlow, result)
            verify(exactly = 1) { repository.searchPostsByTitle(query) }
        }
}