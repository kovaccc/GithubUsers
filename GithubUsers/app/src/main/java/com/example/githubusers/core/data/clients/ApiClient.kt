package com.example.githubusers.core.data.clients

import com.example.githubusers.features.users.data.data_sources.UsersRemoteDataSource
import com.example.githubusers.features.users.data.models.DetailedUserResponse
import com.example.githubusers.features.users.data.models.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient : UsersRemoteDataSource {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @GET("search/users")
    override suspend fun searchUsers(
        @Query("q") query: String, @Query("page") page: Int, @Query("per_page") perPage: Int
    ): UsersResponse

    @GET("users/{loginName}")
    override suspend fun getUserDetails(@Path("loginName") loginName: String): DetailedUserResponse
}