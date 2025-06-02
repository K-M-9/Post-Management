package com.myapplication.post.presentation.screen.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.myapplication.post.R
import com.myapplication.post.databinding.FragmentSearchBinding
import com.myapplication.post.domain.model.Post
import com.myapplication.post.presentation.common.CommentPostDialogFragment
import com.myapplication.post.presentation.common.PostsAdapter
import com.myapplication.post.presentation.common.SavePostDialogFragment
import com.myapplication.post.presentation.util.parseErrorMessage
import com.myapplication.post.presentation.util.showSnackbarWithAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var postAdapter: PostsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        setupUI()
        observeData()
    }

    private fun setupUI() {
        setupRecyclerView()
        setupFilters()
        setupSearchView()
        setupFragmentListeners()
    }

    private fun setupRecyclerView() {
        postAdapter = PostsAdapter(
            onFavoriteClick = viewModel::toggleFavorite,
            onPostClick = ::navigateToPost,
            onDeleteClick = ::showDeleteConfirmation
        )
        binding.searchRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postAdapter
        }
    }

    private fun navigateToPost(post: Post) {
        val action = if (post.isMyPost) {
            SearchFragmentDirections.actionSearchToSaveDialog(
                post.id, post.title, post.body, post.comment.orEmpty()
            )
        } else {
            SearchFragmentDirections.actionSearchToCommentDialog(post.id, post.comment.orEmpty())
        }
        findNavController().navigate(action)
    }

    private fun showDeleteConfirmation(post: Post) {
        binding.root.showSnackbarWithAction(
            message = getString(R.string.delete_my_post),
            backgroundColorRes = R.color.primary_color,
            actionTextRes = R.string.yes_delete,
            duration = Snackbar.LENGTH_SHORT,
            action = { viewModel.deleteMyPost(post) }
        )
    }

    private fun setupFragmentListeners() {
        setFragmentResultListener(SavePostDialogFragment.REQUEST_KEY) { _, bundle ->
            with(bundle) {
                viewModel.savePost(
                    getInt(SavePostDialogFragment.RESULT_POST_ID),
                    getString(SavePostDialogFragment.RESULT_TITLE).orEmpty(),
                    getString(SavePostDialogFragment.RESULT_BODY).orEmpty(),
                    getString(SavePostDialogFragment.RESULT_COMMENT).orEmpty()
                )
            }
        }

        setFragmentResultListener(CommentPostDialogFragment.REQUEST_KEY) { _, bundle ->
            viewModel.updatePostComment(
                bundle.getInt(CommentPostDialogFragment.RESULT_POST_ID, 0),
                bundle.getString(CommentPostDialogFragment.RESULT_COMMENT).orEmpty()
            )
        }
    }

    private fun setupFilters() {
        binding.filterChips.apply {
            setOnCheckedStateChangeListener { _, checkedIds ->
                val filterType = when (checkedIds.firstOrNull()) {
                    R.id.chip_favorites -> SearchViewModel.FilterType.FAVORITES
                    R.id.chip_my -> SearchViewModel.FilterType.MY
                    else -> SearchViewModel.FilterType.ALL
                }
                viewModel.setFilter(filterType)
            }
            check(R.id.chip_all)
        }
    }

    private fun setupSearchView() {
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = handleQuery(query)
                override fun onQueryTextChange(newText: String?) = handleQuery(newText)
            })
            requestFocus()
        }
    }

    private fun handleQuery(query: String?): Boolean {
        query?.let { viewModel.setSearchQuery(it) }
        return true
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { observeLoadState() }
                launch { observePosts() }
            }
        }
    }

    private suspend fun observeLoadState() {
        postAdapter.loadStateFlow
            .map { it.refresh }
            .distinctUntilChanged()
            .collect { loadState ->
                binding.apply {
                    searchProgressBar.isVisible = loadState is LoadState.Loading
                    searchImageError.isVisible = loadState is LoadState.Error
                    searchRecycler.isVisible = loadState is LoadState.NotLoading
                }
                if (loadState is LoadState.Error) {
                    binding.root.showSnackbarWithAction(
                        message = parseErrorMessage(loadState, requireContext()),
                        action = postAdapter::retry
                    )
                }
            }
    }

    private suspend fun observePosts() {
        viewModel.posts.collectLatest { postAdapter.submitData(it) }
    }
}