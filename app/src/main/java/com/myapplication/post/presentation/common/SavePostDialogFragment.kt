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
import com.myapplication.post.databinding.FragmentSavePostDialogBinding

class SavePostDialogFragment : DialogFragment(R.layout.fragment_save_post_dialog) {
    private lateinit var binding: FragmentSavePostDialogBinding
    private val args: SavePostDialogFragmentArgs by navArgs()

    companion object {
        const val REQUEST_KEY = "add_post_request"
        const val RESULT_TITLE = "title"
        const val RESULT_BODY = "body"
        const val RESULT_COMMENT = "comment"
        const val RESULT_POST_ID = "post_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavePostDialogBinding.bind(view)

        setupViews()
        populateFields()
    }

    private fun setupViews() {
        binding.apply {
            saveDialogBtnCancel.setOnClickListener { dismiss() }
            saveDialogBtnSave.setOnClickListener { handleSaveClick() }
        }
    }

    private fun populateFields() {
        binding.apply {
            saveDialogTitleEditText.setText(args.postTitle)
            saveDialogBodyEditText.setText(args.postBody)
            saveDialogCommentEditText.setText(args.postComment)
        }
    }

    private fun handleSaveClick() {
        val currentTitle = binding.saveDialogTitleEditText.text.toString().trim()
        val currentBody = binding.saveDialogBodyEditText.text.toString().trim()
        val currentComment = binding.saveDialogCommentEditText.text.toString().trim()

        val originalTitle = args.postTitle
        val originalBody = args.postBody
        val originalComment = args.postComment

        when {
            currentTitle.isEmpty() -> {
                showTitleError(getString(R.string.error_title_empty))
            }

            currentTitle == originalTitle &&
                    currentBody == originalBody &&
                    currentComment == originalComment -> {
                showTitleError(getString(R.string.error_no_changes))
            }

            else -> {
                clearErrors()
                sendResult(
                    id = args.postId,
                    title = currentTitle,
                    body = currentBody,
                    comment = currentComment
                )
                dismiss()
            }
        }
    }

    private fun showTitleError(message: String) {
        binding.saveDialogTilTitle.apply {
            error = message
            boxStrokeErrorColor = ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.error_color)
            )
        }
    }

    private fun clearErrors() {
        val primaryColor = ContextCompat.getColor(requireContext(), R.color.primary_color)
        binding.apply {
            saveDialogTilTitle.apply {
                error = null
                boxStrokeColor = primaryColor
            }
            saveDialogTilBody.apply {
                error = null
                boxStrokeColor = primaryColor
            }
            saveDialogTilComment.apply {
                error = null
                boxStrokeColor = primaryColor
            }
        }
    }

    private fun sendResult(id: Int, title: String, body: String, comment: String) {
        setFragmentResult(
            REQUEST_KEY,
            bundleOf(
                RESULT_POST_ID to id,
                RESULT_TITLE to title,
                RESULT_BODY to body,
                RESULT_COMMENT to comment
            )
        )
    }
}
