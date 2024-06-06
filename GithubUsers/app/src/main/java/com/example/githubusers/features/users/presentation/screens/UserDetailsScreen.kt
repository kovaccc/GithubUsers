package com.example.githubusers.features.users.presentation.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.githubusers.R
import com.example.githubusers.core.presentation.utils.asUiText
import com.example.githubusers.features.users.presentation.actions.UserUiAction
import com.example.githubusers.features.users.presentation.viewmodels.UserViewModel
import com.example.githubusers.features.users.presentation.widgets.user_details.LoadingContent
import com.example.githubusers.features.users.presentation.widgets.user_details.UserDetailsContent
import com.example.githubusers.features.users.presentation.widgets.user_details.UserDetailsErrorContent

@Composable
fun UserDetailsScreen(userLoginName: String?, viewModel: UserViewModel = hiltViewModel()) {

    LaunchedEffect(userLoginName) {
        if (userLoginName != null) viewModel.accept(UserUiAction.GetDetails(userLoginName))
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

        userLoginName == null -> Text(text = stringResource(R.string.select_user))

        else -> {
            UserDetailsErrorContent(error = uiState.error?.asUiText()?.asString())
        }
    }
}
