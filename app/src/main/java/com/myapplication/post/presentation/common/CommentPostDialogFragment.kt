package com.myapplication.post.presentation.common

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.myapplication.post.R
import com.myapplication.post.databinding.FragmentCommentPostDialogBinding

class CommentPostDialogFragment : DialogFragment(R.layout.fragment_comment_post_dialog) {
    private lateinit var binding: FragmentCommentPostDialogBinding
    private val args: CommentPostDialogFragmentArgs by navArgs()

    companion object {
        const val REQUEST_KEY = "comment_post_request"
        const val RESULT_COMMENT = "comment"
        const val RESULT_POST_ID = "post_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCommentPostDialogBinding.bind(view)
        setupViews()
    }

    private fun setupViews() {
        val originalComment = args.postComment
        binding.apply {
            commentDialogCommentEditText.setText(originalComment)
            commentDialogBtnCancel.setOnClickListener { dismiss() }
            commentDialogBtnSave.setOnClickListener { validateAndSave(originalComment) }
        }
    }

    private fun validateAndSave(originalComment: String) {
        binding.apply {
            val newComment = commentDialogCommentEditText.text.toString().trim()
            when {
                newComment == originalComment -> {
                    showError(getString(R.string.error_change_comment))
                }

                else -> {
                    clearError()
                    sendResult(id = args.postId, comment = newComment)
                    dismiss()
                }
            }
        }
    }

    private fun showError(message: String) = with(binding.commentDialogTilComment) {
        error = message
        boxStrokeErrorColor = ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), R.color.error_color)
        )
    }

    private fun clearError() = with(binding.commentDialogTilComment) {
        error = null
        boxStrokeColor = ContextCompat.getColor(requireContext(), R.color.primary_color)
    }

    private fun sendResult(id: Int, comment: String) {
        setFragmentResult(
            REQUEST_KEY,
            bundleOf(
                RESULT_POST_ID to id,
                RESULT_COMMENT to comment
            )
        )
    }
}