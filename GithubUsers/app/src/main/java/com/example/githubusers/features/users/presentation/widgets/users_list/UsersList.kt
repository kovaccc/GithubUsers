package com.example.githubusers.features.users.presentation.widgets.users_list

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.githubusers.R
import com.example.githubusers.features.users.domain.entities.User
import com.example.githubusers.features.users.presentation.actions.UsersUiAction


@Composable
fun UsersList(
    usersPagingItems: LazyPagingItems<User>,
    onScrollChanged: (UsersUiAction.Scroll) -> Unit,
    onUserClicked: (user: User) -> Unit,
    currentQuery: String,
    hasNotScrolledForCurrentSearch: Boolean
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()

    HandleScrollEffect(listState, currentQuery, onScrollChanged)
    HandleLoadState(usersPagingItems, context)
    HandleScrollOnNewQuery(
        listState = listState,
        hasNotScrolledForCurrentSearch = hasNotScrolledForCurrentSearch
    )

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            usersPagingItems.loadState.refresh is LoadState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            else -> {
                UsersLazyColumn(usersPagingItems, listState, onUserClicked, currentQuery)
            }
        }
    }
}

@Composable
private fun HandleScrollEffect(
    listState: LazyListState,
    currentQuery: String,
    onScrollChanged: (UsersUiAction.Scroll) -> Unit
) {
    LaunchedEffect(key1 = listState, key2 = currentQuery) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { firstVisibleIndex ->
                if (firstVisibleIndex != 0) {
                    onScrollChanged(UsersUiAction.Scroll(currentQuery))
                }
            }
    }
}

@Composable
private fun HandleLoadState(
    usersPagingItems: LazyPagingItems<User>,
    context: Context
) {
    LaunchedEffect(usersPagingItems.loadState) {
        val userPagingLoadState = usersPagingItems.loadState
        val errorState = userPagingLoadState.append as? LoadState.Error
            ?: userPagingLoadState.prepend as? LoadState.Error
            ?: userPagingLoadState.refresh as? LoadState.Error
        errorState?.let {
            val errorMessage =
                context.getString(R.string.error_user_loading_items) + it.error.message
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
private fun HandleScrollOnNewQuery(
    listState: LazyListState,
    hasNotScrolledForCurrentSearch: Boolean
) {
    LaunchedEffect(
        key1 = hasNotScrolledForCurrentSearch,
    ) {
        if (hasNotScrolledForCurrentSearch && listState.firstVisibleItemIndex > 0) {
            listState.scrollToItem(0)
        }
    }
}



