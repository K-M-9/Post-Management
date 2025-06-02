package com.myapplication.post.presentation.screen.home

import android.os.Bundle
import android.view.View
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
import com.myapplication.post.presentation.util.parseErrorMessage
import com.google.android.material.snackbar.Snackbar
import com.myapplication.post.R
import com.myapplication.post.databinding.FragmentHomeBinding
import com.myapplication.post.domain.model.Post
import com.myapplication.post.presentation.common.CommentPostDialogFragment
import com.myapplication.post.presentation.common.PostsAdapter
import com.myapplication.post.presentation.common.SavePostDialogFragment
import com.myapplication.post.presentation.util.showSnackbarWithAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var postAdapter: PostsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        setupUI()
        observeData()
    }

    private fun setupUI() {
        setupRecyclerView()
        setupFab()
        setupFragmentListeners()
    }

    private fun setupRecyclerView() {
        postAdapter = PostsAdapter(
            onFavoriteClick = viewModel::toggleFavorite,
            onPostClick = ::navigateToPostDialog,
            onDeleteClick = ::showDeleteConfirmation
        )
        binding.postsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postAdapter
        }
    }

    private fun setupFab() {
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToSaveDialog(0, "", "", "")
            )
        }
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

    private fun navigateToPostDialog(post: Post) {
        val action = if (post.isMyPost) {
            HomeFragmentDirections.actionHomeToSaveDialog(
                post.id, post.title, post.body, post.comment.orEmpty()
            )
        } else {
            HomeFragmentDirections.actionHomeToCommentDialog(post.id, post.comment.orEmpty())
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
                    homeProgressBar.isVisible = loadState is LoadState.Loading
                    homeImageError.isVisible = loadState is LoadState.Error
                    postsRecycler.isVisible = loadState is LoadState.NotLoading
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

    override fun onDestroyView() {
        binding.postsRecycler.adapter = null
        super.onDestroyView()
    }
}