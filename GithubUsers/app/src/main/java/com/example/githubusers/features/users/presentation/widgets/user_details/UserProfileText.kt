package com.example.githubusers.features.users.presentation.widgets.user_details

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.githubusers.R
import com.example.githubusers.features.users.domain.entities.DetailedUser


@Composable
fun UserProfileText(user: DetailedUser) {
    Text(
        text = user.username ?: "",
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = stringResource(
            R.string.details_name,
            user.name ?: stringResource(R.string.n_a)
        ),
        style = MaterialTheme.typography.bodyMedium
    )
    Text(
        text = stringResource(
            R.string.details_company,
            user.company ?: stringResource(R.string.n_a)
        ),
        style = MaterialTheme.typography.bodyMedium
    )
    Text(
        text = stringResource(R.string.details_public_repos, user.publicRepos),
        style = MaterialTheme.typography.bodyMedium
    )
    Text(
        text = stringResource(
            R.string.details_hireable,
            if (user.hireable == true) stringResource(R.string.yes) else stringResource(R.string.no)
        ),
        style = MaterialTheme.typography.bodyMedium
    )
}