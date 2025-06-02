package com.myapplication.post.presentation.util

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.myapplication.post.R
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbarWithAction(
    message: String,
    action: () -> Unit,
    duration: Int =  Snackbar.LENGTH_INDEFINITE,
    @StringRes actionTextRes: Int = R.string.retry,
    @ColorRes backgroundColorRes: Int = R.color.error_color,
    @ColorRes textColorRes: Int = R.color.snackbar_text_color,
    @ColorRes actionTextColorRes: Int = R.color.snackbar_text_color
) {
    val snackbar = Snackbar.make(this, message, duration)
    snackbar.setAction(context.getString(actionTextRes)) { action() }
    snackbar.setBackgroundTint(ContextCompat.getColor(context, backgroundColorRes))
    snackbar.setTextColor(ContextCompat.getColor(context, textColorRes))
    snackbar.setActionTextColor(ContextCompat.getColor(context, actionTextColorRes))
    snackbar.show()
}


fun View.showErrorSnackbar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    @ColorRes backgroundColorRes: Int = R.color.primary_color,
    @ColorRes textColorRes: Int = R.color.snackbar_text_color
) {
    val snackbar = Snackbar.make(this, message, duration)
    snackbar.setBackgroundTint(ContextCompat.getColor(context, backgroundColorRes))
    snackbar.setTextColor(ContextCompat.getColor(context, textColorRes))
    snackbar.show()
}