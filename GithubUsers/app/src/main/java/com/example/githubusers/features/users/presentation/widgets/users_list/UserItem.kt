package com.example.githubusers.features.users.presentation.widgets.users_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.githubusers.R
import com.example.githubusers.features.users.domain.entities.User

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
            Text(text = stringResource(R.string.id, user.id), fontSize = 20.sp)
            Text(
                text = stringResource(R.string.username, user.username as String),
                fontSize = 18.sp
            )
        }
    }
}