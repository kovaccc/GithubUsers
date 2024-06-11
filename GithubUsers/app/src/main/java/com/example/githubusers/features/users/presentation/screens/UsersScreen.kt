package com.example.githubusers.features.users.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.githubusers.features.users.domain.entities.User
import com.example.githubusers.features.users.presentation.actions.UsersUiAction
import com.example.githubusers.features.users.presentation.viewmodels.UsersViewModel
import com.example.githubusers.features.users.presentation.widgets.users_list.SearchBar
import com.example.githubusers.features.users.presentation.widgets.users_list.UsersList

@Composable
fun UsersScreen(
    viewModel: UsersViewModel = hiltViewModel(), onUserClicked: (user: User) -> Unit,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val usersPagingItems = viewModel.usersPagingDataFlow.collectAsLazyPagingItems()

    Column {
        SearchBar(
            query = uiState.query,
            onQueryChanged = { query ->
                viewModel.accept(UsersUiAction.Search(query))
            }
        )
        UsersList(
            usersPagingItems = usersPagingItems,
            onScrollChanged = { action ->
                viewModel.accept(action)
            },
            onUserClicked = onUserClicked,
            currentQuery = uiState.query,
            hasNotScrolledForCurrentSearch = uiState.hasNotScrolledForCurrentSearch
        )
    }
}
