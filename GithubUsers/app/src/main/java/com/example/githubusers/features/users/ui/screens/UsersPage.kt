package com.example.githubusers.features.users.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.githubusers.features.users.ui.viewmodels.UsersViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.githubusers.features.users.domain.entities.User
import com.example.githubusers.features.users.ui.actions.UsersUiAction

@Composable
fun UsersScreen(
    viewModel: UsersViewModel = hiltViewModel(), onUserClicked: (user: User) -> Unit,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val usersPagingItems = viewModel.usersPagingDataFlow.collectAsLazyPagingItems()

    Column {
        SearchBar(
            query = uiState.query,
            onQueryChanged = { query -> viewModel.accept(UsersUiAction.Search(query)) }
        )
        UsersList(
            usersPagingItems = usersPagingItems,
            onScrollChanged = { action -> viewModel.accept(action) },
            onUserClicked = onUserClicked
        )
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit
) {
    var text by remember { mutableStateOf(query) }

    TextField(
        value = text,
        onValueChange = {
            text = it
            onQueryChanged(it)
        },
        label = { Text("Search") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun UsersList(
    usersPagingItems: LazyPagingItems<User>,
    onScrollChanged: (UsersUiAction.Scroll) -> Unit,
    onUserClicked: (photo: User) -> Unit
) {
    val listState = rememberLazyListState()

    // Handle scrolling state
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { firstVisibleItemIndex ->
                val firstVisibleItem = listState.layoutInfo.visibleItemsInfo.firstOrNull()
                if (firstVisibleItem != null) {
                    onScrollChanged(
                        UsersUiAction.Scroll(
                            currentQuery = firstVisibleItemIndex.toString()
                        )
                    )
                }
            }
    }

    LazyColumn(state = listState) {
        if (usersPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                Text(
                    text = "Waiting for items to load from the backend",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            }
        }

        items(count = usersPagingItems.itemCount,
            key = usersPagingItems.itemKey { it.id }
        ) { index ->
            val item = usersPagingItems[index]
            if (item != null)
                UserItem(item, onUserClicked = onUserClicked)
        }

        if (usersPagingItems.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            }
        }

        if (usersPagingItems.loadState.append is LoadState.Error) {
            item {
                Text(
                    text = "Error loading more items",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun UserItem(user: User, onUserClicked: (photo: User) -> Unit) {
    Surface(
        modifier = Modifier.clickable { onUserClicked(user) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "ID: ${user.id}", fontSize = 20.sp)
            Text(text = "Username: ${user.username}", fontSize = 18.sp)
        }
    }
}