package com.example.githubusers.features.users.ui.actions

sealed class UserUiAction {
    data class GetDetails(val loginName: String) : UserUiAction()
}
