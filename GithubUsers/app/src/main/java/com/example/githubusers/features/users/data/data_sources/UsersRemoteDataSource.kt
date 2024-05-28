package com.example.githubusers.features.users.data.data_sources

import com.example.githubusers.features.users.data.models.UsersResponse

interface UsersRemoteDataSource {
    suspend fun searchUsers(query: String, page: Int, perPage: Int): UsersResponse
}
