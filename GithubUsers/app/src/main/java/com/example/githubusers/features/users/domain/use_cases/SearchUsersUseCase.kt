package com.example.githubusers.features.users.domain.use_cases

import androidx.paging.PagingData
import com.example.githubusers.features.users.domain.entities.User
import com.example.githubusers.features.users.domain.repositories.UsersRepository
import com.example.githubusers.features.users.domain.utils.UsersDomainConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchUsersUseCase @Inject constructor(private val githubUsersRepository: UsersRepository) {
    fun execute(query: String) =
        if (query.length > UsersDomainConstants.SEARCH_TRIGGER_KEYSTROKE)
            githubUsersRepository.getSearchedUsersStream(query)
        else
            flowOf(PagingData.empty())
}
