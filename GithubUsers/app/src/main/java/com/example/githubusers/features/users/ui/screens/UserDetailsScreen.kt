package com.example.githubusers.features.users.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.githubusers.core.ui.utils.asUiText
import com.example.githubusers.features.users.ui.actions.UserUiAction
import com.example.githubusers.features.users.ui.viewmodels.UserViewModel
import com.example.githubusers.features.users.ui.widgets.LoadingContent
import com.example.githubusers.features.users.ui.widgets.UserDetailsContent
import com.example.githubusers.features.users.ui.widgets.UserDetailsErrorContent

@Composable
fun UserDetailsScreen(userLoginName: String?, viewModel: UserViewModel = hiltViewModel()) {

    LaunchedEffect(true) {
        viewModel.accept(UserUiAction.GetDetails(userLoginName ?: ""))
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val detailedUser = uiState.detailedUser

    when {
        detailedUser != null -> {
            UserDetailsContent(user = detailedUser)
        }

        uiState.isLoading -> {
            LoadingContent()
        }

        else -> {
            UserDetailsErrorContent(error = uiState.error?.asUiText()?.asString())
        }
    }
}
