package com.example.githubusers.features.users.ui.states

import com.example.githubusers.core.domain.errors.Error
import com.example.githubusers.features.users.domain.entities.DetailedUser

data class UserUiState(
    val detailedUser: DetailedUser? = null,
    val isLoading: Boolean = false,
    val error: Error? = null,
)
