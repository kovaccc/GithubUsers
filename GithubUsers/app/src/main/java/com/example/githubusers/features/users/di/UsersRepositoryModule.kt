package com.example.githubusers.features.users.di

import com.example.githubusers.features.users.data.repositories.UsersRepositoryImpl
import com.example.githubusers.features.users.domain.repositories.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UsersRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindGithubUsersRepository(
        githubUsersRepositoryImpl: UsersRepositoryImpl
    ): UsersRepository
}