package com.myapplication.post.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.myapplication.post.domain.model.Post
import com.myapplication.post.domain.usecase.DeleteMyPostUseCase
import com.myapplication.post.domain.usecase.GetAllPostsUseCase
import com.myapplication.post.domain.usecase.GetFavoritePostsUseCase
import com.myapplication.post.domain.usecase.GetMyPostsUseCase
import com.myapplication.post.domain.usecase.SavePostsUseCase
import com.myapplication.post.domain.usecase.SearchPostsByTitleUseCase
import com.myapplication.post.domain.usecase.ToggleFavoriteUseCase
import com.myapplication.post.domain.usecase.UpdatePostCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val getAllPostsUseCase: GetAllPostsUseCase,
    private val getFavoritePostsUseCase: GetFavoritePostsUseCase,
    private val getMyPostsUseCase: GetMyPostsUseCase,
    private val updateCommentUseCase: UpdatePostCommentUseCase,
    private val savePostsUseCase: SavePostsUseCase,
    private val searchPostsByTitleUseCase: SearchPostsByTitleUseCase,
    private val deleteMyPostUseCase: DeleteMyPostUseCase
) : ViewModel() {

    private val _filter = MutableStateFlow(FilterType.ALL)
    private val _query = MutableStateFlow("")

    val posts: Flow<PagingData<Post>> = combine(_filter, _query) { filter, query ->
        when {
            filter == FilterType.ALL && query.isBlank() -> getAllPostsUseCase()
            filter == FilterType.ALL && query.isNotBlank() -> searchPostsByTitleUseCase(query)
            filter == FilterType.FAVORITES -> getFavoritePostsUseCase()
            filter == FilterType.MY -> getMyPostsUseCase()
            else -> getAllPostsUseCase()
        }
    }.flatMapLatest { baseFlow ->
            if (_filter.value != FilterType.ALL && _query.value.isNotBlank()) {
                baseFlow.map { pagingData ->
                    pagingData.filter { it.title.contains(_query.value, ignoreCase = true) }
                }
            } else baseFlow
        }
        .cachedIn(viewModelScope)

    fun setFilter(filterType: FilterType) {
        _filter.value = filterType
    }

    fun setSearchQuery(query: String) {
        _query.value = query
    }

    fun toggleFavorite(post: Post) = viewModelScope.launch { toggleFavoriteUseCase(post.id) }
    fun deleteMyPost(post: Post) = viewModelScope.launch { deleteMyPostUseCase(post.id) }

    fun savePost(id: Int, title: String, body: String, comment: String) = viewModelScope.launch {
        savePostsUseCase(
            Post(
                id = id,
                title = title,
                body = body,
                comment = comment.takeIf { it.isNotBlank() },
                isMyPost = true,
                userId = 0
            )
        )
    }

    fun updatePostComment(id: Int, comment: String?) = viewModelScope.launch {
        updateCommentUseCase(id, comment)
    }

    enum class FilterType { ALL, FAVORITES, MY }
}