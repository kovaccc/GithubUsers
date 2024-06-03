package com.example.githubusers.features.users.data.mappers

import com.example.githubusers.features.users.data.models.DetailedUserResponse
import com.example.githubusers.features.users.domain.entities.DetailedUser

fun DetailedUserResponse.asEntity() = DetailedUser(
    id = id,
    username = login,
    avatarUrl = avatarUrl,
    name = name,
    company = company,
    publicRepos = publicRepos,
    hireable = hireable
)