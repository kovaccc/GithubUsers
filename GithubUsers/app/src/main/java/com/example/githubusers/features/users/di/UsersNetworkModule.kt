package com.example.githubusers.features.users.di

import com.example.githubusers.core.data.clients.ApiClient
import com.example.githubusers.features.users.data.data_sources.UsersRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UsersNetworkModule {

    @Binds
    @Singleton
    abstract fun bindGithubUsersRemoteDataSource(
        apiClient: ApiClient
    ): UsersRemoteDataSource
}