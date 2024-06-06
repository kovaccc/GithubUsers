package com.example.githubusers.features.users.presentation.actions

sealed class UserUiAction {
    data class GetDetails(val loginName: String) : UserUiAction()
}
