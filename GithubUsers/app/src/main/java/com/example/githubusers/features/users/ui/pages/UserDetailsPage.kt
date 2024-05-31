package com.example.githubusers.features.users.ui.pages

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.githubusers.features.users.domain.entities.User
import com.example.githubusers.features.users.ui.viewmodels.UserViewModel

@Composable
fun UserDetailsPage(user: User?, viewModel: UserViewModel = hiltViewModel()) {

    LaunchedEffect(true) {
        viewModel.fetchUserDetails( user?.username ?: "")
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val detailedUser = uiState.detailedUser
    detailedUser?.let { detailedUser ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(detailedUser.avatarUrl),
                contentDescription = null,
                modifier = Modifier.size(128.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = detailedUser.username ?: "",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Name: ${detailedUser.name ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Company: ${detailedUser.company ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Public Repos: ${detailedUser.publicRepos}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Hireable: ${if (detailedUser.hireable == true) "Yes" else "No"}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    } ?: run {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = uiState.errorMessage ?: "", color = Color.Red)
        }
    }
}