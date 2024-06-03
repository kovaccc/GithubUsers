package com.example.githubusers.features.users.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubusers.features.users.domain.entities.User
import com.example.githubusers.features.users.domain.use_cases.SearchUsersUseCase
import com.example.githubusers.features.users.ui.actions.UsersUiAction
import com.example.githubusers.features.users.ui.states.UsersUiState
import com.example.githubusers.features.users.ui.utils.UsersPresentationConstants
import com.example.githubusers.features.users.ui.utils.UsersPresentationConstants.DEFAULT_QUERY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class UsersViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: StateFlow<UsersUiState>
    val pagingDataFlow: Flow<PagingData<User>>
    val accept: (UsersUiAction) -> Unit

    init {
        val initialQuery = savedStateHandle[LAST_SEARCH_QUERY] ?: DEFAULT_QUERY
        val lastQueryScrolled = savedStateHandle[LAST_QUERY_SCROLLED] ?: DEFAULT_QUERY
        val actionStateFlow = MutableSharedFlow<UsersUiAction>()

        val searches = actionStateFlow
            .filterIsInstance<UsersUiAction.Search>()
            .distinctUntilChanged()
            .sample(UsersPresentationConstants.SEARCH_THROTTLE_DURATION)
            .onStart { emit(UsersUiAction.Search(query = initialQuery)) }

        val queriesScrolled = actionStateFlow
            .filterIsInstance<UsersUiAction.Scroll>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(UsersPresentationConstants.STOP_SHARING_POLICY_DELAY),
                replay = 1
            )
            .onStart { emit(UsersUiAction.Scroll(currentQuery = lastQueryScrolled)) }

        pagingDataFlow = searches
            .flatMapLatest {
                searchUsers(queryString = it.query)
            }
            .cachedIn(viewModelScope)

        state = combine(
            searches,
            queriesScrolled,
            ::Pair
        ).map { (search, scroll) ->
            UsersUiState(
                query = search.query,
                lastQueryScrolled = scroll.currentQuery,
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(UsersPresentationConstants.STOP_SHARING_POLICY_DELAY),
                initialValue = UsersUiState()
            )

        accept = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }
    }

    private fun searchUsers(queryString: String): Flow<PagingData<User>> =
        searchUsersUseCase(queryString)

    override fun onCleared() {
        savedStateHandle[LAST_SEARCH_QUERY] = state.value.query
        savedStateHandle[LAST_QUERY_SCROLLED] = state.value.lastQueryScrolled
        super.onCleared()
    }
}

private const val LAST_QUERY_SCROLLED: String = "last_query_scrolled"
private const val LAST_SEARCH_QUERY: String = "last_search_query"

