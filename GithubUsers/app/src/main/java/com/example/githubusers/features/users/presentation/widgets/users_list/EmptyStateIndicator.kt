package com.example.githubusers.features.users.presentation.widgets.users_list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.githubusers.R
import com.example.githubusers.features.users.utils.UsersConstants

@Composable
fun EmptyStateIndicator(currentQuery: String) {
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