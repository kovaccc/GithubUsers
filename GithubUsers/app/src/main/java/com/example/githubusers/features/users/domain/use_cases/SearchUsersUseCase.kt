package com.example.githubusers.features.users.domain.use_cases

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.example.githubusers.features.users.domain.repositories.UsersRepository
import com.example.githubusers.features.users.utils.UsersConstants
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(private val usersRepository: UsersRepository) {
    operator fun invoke(query: String) =
        if (query.length > UsersConstants.SEARCH_TRIGGER_KEYSTROKE)
            usersRepository.getSearchUsersStream(query)
        else
            flowOf(
                PagingData.empty(
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.NotLoading(endOfPaginationReached = true),
                        append = LoadState.NotLoading(endOfPaginationReached = true),
                        prepend = LoadState.NotLoading(endOfPaginationReached = true)
                    )
                )
            )
}
