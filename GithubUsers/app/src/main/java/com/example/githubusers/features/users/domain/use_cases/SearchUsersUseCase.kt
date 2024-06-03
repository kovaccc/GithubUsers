package com.example.githubusers.features.users.domain.use_cases

import androidx.paging.PagingData
import com.example.githubusers.features.users.domain.repositories.UsersRepository
import com.example.githubusers.features.users.domain.utils.UsersDomainConstants
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(private val usersRepository: UsersRepository) {
    operator fun invoke(query: String) =
        if (query.length > UsersDomainConstants.SEARCH_TRIGGER_KEYSTROKE)
            usersRepository.getSearchUsersStream(query)
        else
            flowOf(PagingData.empty())
}
