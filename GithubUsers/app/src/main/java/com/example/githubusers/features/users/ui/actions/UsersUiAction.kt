package com.example.githubusers.features.users.ui.actions

sealed class UsersUiAction {
    data class Search(val query: String) : UsersUiAction()
    data class Scroll(val currentQuery: String) : UsersUiAction()
}
