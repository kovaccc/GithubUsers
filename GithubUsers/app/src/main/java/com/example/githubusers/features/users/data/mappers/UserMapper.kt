package com.example.githubusers.features.users.data.mappers

import com.example.githubusers.features.users.data.models.UserResponse
import com.example.githubusers.features.users.domain.entities.User

fun UserResponse.asEntity() = User(
    id = id,
    username = login,
    avatarUrl = avatarUrl
)