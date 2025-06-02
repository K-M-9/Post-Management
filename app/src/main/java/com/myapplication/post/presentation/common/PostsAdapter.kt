package com.myapplication.post.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myapplication.post.R
import com.myapplication.post.databinding.PostCardBinding
import com.myapplication.post.domain.model.Post

class PostsAdapter(
    private val onFavoriteClick: (Post) -> Unit,
    private val onDeleteClick: (Post) -> Unit,
    private val onPostClick: (Post) -> Unit
) : PagingDataAdapter<Post, PostsAdapter.PostViewHolder>(POST_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onFavoriteClick,
            onDeleteClick,
            onPostClick
        )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    inner class PostViewHolder(
        private val binding: PostCardBinding,
        private val onFavoriteClick: (Post) -> Unit,
        private val onDeleteClick: (Post) -> Unit,
        private val onPostClick: (Post) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                ivFavorite.setOnClickListener { getCurrentPost()?.let(onFavoriteClick) }
                cardPost.setOnClickListener { getCurrentPost()?.let(onPostClick) }
                ivDelete.setOnClickListener { getCurrentPost()?.let(onDeleteClick) }
            }
        }

        fun bind(post: Post) = binding.run {
            tvPostTitle.text = post.title
            tvPostBody.text = post.body

            ivFavorite.setImageResource(
                if (post.isFavorite) R.drawable.ic_favorite_true
                else R.drawable.ic_favorite_false
            )

            ivDelete.isVisible = post.isMyPost


            tvPostUser.text = if (post.isMyPost) {
                root.context.getString(R.string.user_my)
            } else {
                root.context.getString(R.string.user_id_format, post.userId)
            }

            post.comment?.takeIf { it.isNotBlank() }?.let {
                tvPostComment.text = it
                tvPostComment.isVisible = true
            } ?: run {
                tvPostComment.isVisible = false
            }
        }

        private fun getCurrentPost(): Post? =
            bindingAdapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                ?.let { getItem(it) }
    }

    companion object {
        private val POST_COMPARATOR = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem == newItem
        }
    }
}