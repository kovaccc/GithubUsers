package com.example.githubusers.features.users.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusers.core.domain.util.ClientError
import com.example.githubusers.core.domain.util.NetworkError
import com.example.githubusers.core.domain.util.ServerError
import com.example.githubusers.core.domain.util.UnknownError
import com.example.githubusers.core.domain.util.UserDetailsError
import com.example.githubusers.features.users.domain.use_cases.GetUserDetailsUseCase
import com.example.githubusers.features.users.ui.states.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val getUserDetailsUseCase: GetUserDetailsUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(UserState())
    val uiState: StateFlow<UserState> = _uiState.asStateFlow()

    fun fetchUserDetails(userId: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true,
                )
            }
            when (val result = getUserDetailsUseCase.getUserDetails(userId)) {

                is com.example.githubusers.core.domain.util.Result.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            detailedUser = result.data,
                            isLoading = false,
                            errorMessage = null,
                        )
                    }
                }

                is com.example.githubusers.core.domain.util.Result.Error -> {
                    val message = when (result.error) {
                        is ClientError -> ""
                        is NetworkError -> ""
                        is ServerError -> ""
                        is UnknownError -> ""
                        is UserDetailsError.NotFoundError -> result.error.message
                    }
                    _uiState.update { currentState ->
                        currentState.copy(
                            detailedUser = null,
                            isLoading = false,
                            errorMessage = message,
                        )
                    }
                }
            }
        }
    }
}
