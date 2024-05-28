package com.example.githubusers.features.users.data.mappers

import com.example.githubusers.core.data.mappers.ResponseMapper
import com.example.githubusers.features.users.data.models.UserResponse
import com.example.githubusers.features.users.domain.entities.User
import javax.inject.Inject

class UserResponseMapper @Inject constructor() : ResponseMapper<User, UserResponse> {
    override operator fun invoke(response: UserResponse): User {
        return User(
            id = response.id,
            username = response.login,
            avatarUrl = response.avatarUrl
        )
    }
}
