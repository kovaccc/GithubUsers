package com.example.githubusers.features.users.domain.repositories

import androidx.paging.PagingData
import com.example.githubusers.core.domain.util.Error
import com.example.githubusers.core.domain.util.Result
import com.example.githubusers.features.users.domain.entities.DetailedUser
import com.example.githubusers.features.users.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getSearchedUsersStream(query: String): Flow<PagingData<User>>

    suspend fun getUserDetails(loginName: String): Result<DetailedUser, Error>
}
