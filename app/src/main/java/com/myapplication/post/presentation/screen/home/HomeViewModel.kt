package com.myapplication.post.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myapplication.post.domain.model.Post
import com.myapplication.post.domain.usecase.DeleteMyPostUseCase
import com.myapplication.post.domain.usecase.GetAllPostsUseCase
import com.myapplication.post.domain.usecase.SavePostsUseCase
import com.myapplication.post.domain.usecase.ToggleFavoriteUseCase
import com.myapplication.post.domain.usecase.UpdatePostCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.takeIf
import kotlin.text.isNotBlank

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAllPostsUseCase: GetAllPostsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val savePostsUseCase: SavePostsUseCase,
    private val updateCommentUseCase: UpdatePostCommentUseCase,
    private val deleteMyPostUseCase: DeleteMyPostUseCase
) : ViewModel() {

    val posts: Flow<PagingData<Post>> = getAllPostsUseCase().cachedIn(viewModelScope)

    fun toggleFavorite(post: Post) = viewModelScope.launch {
        toggleFavoriteUseCase(post.id)
    }

    fun updatePostComment(id: Int, comment: String) = viewModelScope.launch {
        updateCommentUseCase(id, comment.takeIf { it.isNotBlank() })
    }

    fun deleteMyPost(post: Post) = viewModelScope.launch {
        deleteMyPostUseCase(post.id)
    }

    fun savePost(id: Int, title: String, body: String, comment: String) = viewModelScope.launch {
        savePostsUseCase(
            Post(
                id = id,
                title = title,
                body = body,
                comment = comment.takeIf { it.isNotBlank() },
                userId = 0,
                isMyPost = true
            )
        )
    }
}
