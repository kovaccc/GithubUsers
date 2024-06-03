package com.example.githubusers.features.users.ui.widgets.user_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.githubusers.features.users.domain.entities.DetailedUser

@Composable
fun UserDetailsContent(user: DetailedUser) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserProfileImage(avatarUrl = user.avatarUrl ?: "")
        Spacer(modifier = Modifier.height(16.dp))
        UserProfileText(user = user)
    }
}
