package com.example.githubusers.features.users.presentation.actions

sealed class UsersUiAction {
    data class Search(val query: String) : UsersUiAction()
    data class Scroll(val currentQuery: String) : UsersUiAction()
}
