package com.example.githubusers.features.users.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusers.core.domain.util.Result
import com.example.githubusers.features.users.domain.use_cases.GetUserDetailsUseCase
import com.example.githubusers.features.users.ui.actions.UserUiAction
import com.example.githubusers.features.users.ui.actions.UsersUiAction
import com.example.githubusers.features.users.ui.states.UserUiState
import com.example.githubusers.features.users.ui.utils.UsersPresentationConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val getUserDetailsUseCase: GetUserDetailsUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    val accept: (UserUiAction) -> Unit

    private var fetchJob: Job? = null

    init {
        accept = { action ->
            when (action) {
                is UserUiAction.GetDetails -> fetchUserDetails(action.loginName)
            }
        }
    }

    fun fetchUserDetails(userName: String) {
        Log.d("MATEJ", "matej1 $userName")
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                )
            }
            when (val result = getUserDetailsUseCase(userName)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            detailedUser = result.data,
                            isLoading = false,
                            error = null,
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            detailedUser = null,
                            isLoading = false,
                            error = result.error,
                        )
                    }
                }
            }
        }
    }
}
