package com.example.githubusers.features.users.presentation.widgets.users_list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.githubusers.R

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit
) {
    var text by remember { mutableStateOf(query) }

    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
            onQueryChanged(newText)
        },
        label = { Text(stringResource(R.string.search_github_users)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}