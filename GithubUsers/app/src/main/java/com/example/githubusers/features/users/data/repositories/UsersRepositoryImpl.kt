package com.example.githubusers.features.users.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.githubusers.features.users.data.data_sources.UsersPagingSource
import com.example.githubusers.features.users.data.data_sources.UsersRemoteDataSource
import com.example.githubusers.features.users.data.mappers.UserResponseMapper
import com.example.githubusers.features.users.domain.entities.User
import com.example.githubusers.features.users.domain.repositories.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val githubUsersRemoteDataSource: UsersRemoteDataSource,
    private val userResponseMapper: UserResponseMapper
) : UsersRepository {
    override fun getSearchedUsersStream(query: String): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                UsersPagingSource(
                    githubUsersRemoteDataSource,
                    query
                )
            }
        ).flow.map { pagingUserResponses ->
            pagingUserResponses.map { userResponse -> userResponseMapper(userResponse) }
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}
