package com.example.githubusers.features.users.ui.states

import com.example.githubusers.features.users.domain.entities.DetailedUser

data class UserState(
    val detailedUser: DetailedUser? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
