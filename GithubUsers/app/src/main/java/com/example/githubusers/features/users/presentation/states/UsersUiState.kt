package com.example.githubusers.features.users.presentation.states

import com.example.githubusers.features.users.presentation.utils.UsersPresentationConstants.DEFAULT_QUERY


data class UsersUiState(
    val query: String = DEFAULT_QUERY,
    val lastQueryScrolled: String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch: Boolean = false,
)
