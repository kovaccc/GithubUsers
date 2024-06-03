package com.example.githubusers.core.ui.utils

import com.example.githubusers.R
import com.example.githubusers.core.domain.errors.ClientError
import com.example.githubusers.core.domain.errors.Error
import com.example.githubusers.core.domain.errors.NetworkError
import com.example.githubusers.core.domain.errors.ServerError
import com.example.githubusers.core.domain.errors.UnknownError
import com.example.githubusers.core.domain.util.Result
import com.example.githubusers.features.users.domain.errors.UserDetailsError

fun Error.asUiText(): UiText {
    return when (this) {
        is ClientError -> UiText.StringResource(R.string.client_error)
        is NetworkError -> UiText.StringResource(R.string.network_error)
        is ServerError -> UiText.StringResource(R.string.server_error)
        is UnknownError -> UiText.StringResource(R.string.unknown_error)
        is UserDetailsError -> when (this) {
            is UserDetailsError.NotFoundError -> UiText.DynamicString(this.message ?: "")
        }

        else -> UiText.StringResource(R.string.unknown_error)
    }
}

fun Result.Error<*, Error>.asErrorUiText(): UiText {
    return error.asUiText()
}
