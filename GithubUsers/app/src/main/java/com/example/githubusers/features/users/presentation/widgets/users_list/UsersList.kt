package com.example.githubusers.features.users.presentation.widgets.users_list

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.githubusers.R
import com.example.githubusers.core.domain.errors.Error
import com.example.githubusers.core.presentation.utils.asUiText
import com.example.githubusers.features.users.domain.entities.User
import com.example.githubusers.features.users.presentation.actions.UsersUiAction
import com.example.githubusers.features.users.utils.UsersConstants


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
private fun UsersLazyColumn(
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
        if (usersPagingItems.loadState.refresh is LoadState.Error) {
            val errorMessage = context.getString(R.string.error_user_loading_items) +
                    (usersPagingItems.loadState.refresh as LoadState.Error).error.message
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


@Composable
private fun ErrorIndicator(retry: () -> Unit, error: Error) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = error.asUiText().asString(),
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = retry,
                contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.retry),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
private fun EmptyStateIndicator(currentQuery: String) {
    val textResId = if (currentQuery.length < UsersConstants.SEARCH_TRIGGER_KEYSTROKE) {
        R.string.enter_at_least_three_characters
    } else {
        R.string.no_users_found_for_the_entered_query
    }
    Text(
        text = stringResource(id = textResId),
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(16.dp)
    )
}

@Composable
private fun LoadingMoreIndicator() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(16.dp)
    )
}
