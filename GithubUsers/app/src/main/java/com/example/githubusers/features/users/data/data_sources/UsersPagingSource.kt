package com.example.githubusers.features.users.data.data_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubusers.core.data.error_resolvers.BaseApiErrorResolver
import com.example.githubusers.features.users.data.models.UserResponse
import com.example.githubusers.features.users.data.repositories.UsersRepositoryImpl.Companion.NETWORK_PAGE_SIZE

private const val GITHUB_STARTING_PAGE_INDEX: Int = 1

class UsersPagingSource(
    private val usersRemoteDataSource: UsersRemoteDataSource,
    private val query: String,
    private val baseApiErrorResolver: BaseApiErrorResolver
) : PagingSource<Int, UserResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserResponse> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        return try {
            val response = usersRemoteDataSource.searchUsers(query, position, params.loadSize)
            val users = response.items
            val nextKey = if (users.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = users,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            return LoadResult.Error(baseApiErrorResolver.resolve(exception))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}