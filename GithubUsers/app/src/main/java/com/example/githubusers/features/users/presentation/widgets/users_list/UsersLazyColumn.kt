package com.example.githubusers.features.users.presentation.widgets.users_list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.githubusers.core.domain.errors.Error
import com.example.githubusers.features.users.domain.entities.User

@Composable
fun UsersLazyColumn(
    usersPagingItems: LazyPagingItems<User>,
    listState: LazyListState,
    onUserClicked: (user: User) -> Unit,
    currentQuery: String
) {
    LazyColumn(state = listState) {
        items(usersPagingItems.itemCount, key = usersPagingItems.itemKey { it.id }) { index ->
            usersPagingItems[index]?.let { user ->
                UserItem(user, onUserClicked)
            }
        }

        if (usersPagingItems.loadState.append == LoadState.Loading) {
            item { LoadingMoreIndicator() }
        }

        item {
            val refreshError = usersPagingItems.loadState.refresh as? LoadState.Error
            val appendError = usersPagingItems.loadState.append as? LoadState.Error

            if (refreshError != null || appendError != null) {
                ErrorIndicator(
                    retry = { usersPagingItems.retry() },
                    error = refreshError?.error as Error? ?: appendError!!.error as Error
                )
            } else if (usersPagingItems.itemSnapshotList.isEmpty()) {
                EmptyStateIndicator(currentQuery)
            }
        }
    }
}