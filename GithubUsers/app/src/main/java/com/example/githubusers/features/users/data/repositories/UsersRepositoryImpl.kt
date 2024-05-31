package com.example.githubusers.features.users.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.githubusers.core.data.error_resolvers.BaseApiErrorResolver
import com.example.githubusers.core.data.utils.execute
import com.example.githubusers.core.domain.util.Error
import com.example.githubusers.core.domain.util.Result
import com.example.githubusers.features.users.data.data_sources.UsersPagingSource
import com.example.githubusers.features.users.data.data_sources.UsersRemoteDataSource
import com.example.githubusers.features.users.data.error_resolvers.GetUserDetailsErrorResolver
import com.example.githubusers.features.users.data.mappers.DetailedUserResponseMapper
import com.example.githubusers.features.users.data.mappers.UserResponseMapper
import com.example.githubusers.features.users.domain.entities.DetailedUser
import com.example.githubusers.features.users.domain.entities.User
import com.example.githubusers.features.users.domain.repositories.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersRemoteDataSource: UsersRemoteDataSource,
    private val userResponseMapper: UserResponseMapper,
    private val detailedUserResponseMapper: DetailedUserResponseMapper,
    private val baseApiErrorResolver: BaseApiErrorResolver
) : UsersRepository {
    override fun getSearchedUsersStream(query: String): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                UsersPagingSource(
                    usersRemoteDataSource,
                    query,
                    baseApiErrorResolver
                )
            }
        ).flow.map { pagingUserResponses ->
            pagingUserResponses.map { userResponse -> userResponseMapper(userResponse) }
        }
    }

    override suspend fun getUserDetails(loginName: String): Result<DetailedUser, Error> {
        return execute(
            function = {
                val response = usersRemoteDataSource.getUserDetails(loginName)
                Result.Success(detailedUserResponseMapper(response))
            },
            errorResolver = GetUserDetailsErrorResolver(),
        )
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}
