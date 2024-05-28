package com.example.githubusers.features.users.di

import com.example.githubusers.core.data.mappers.ResponseMapper
import com.example.githubusers.features.users.data.mappers.UserResponseMapper
import com.example.githubusers.features.users.data.models.UserResponse
import com.example.githubusers.features.users.domain.entities.User
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UsersMappersModule {

    @Binds
    @Singleton
    abstract fun bindUserResponseMapper(
        userResponseMapper: UserResponseMapper
    ): ResponseMapper<User, UserResponse>
}
