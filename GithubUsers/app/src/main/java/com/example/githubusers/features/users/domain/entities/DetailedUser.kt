package com.example.githubusers.features.users.domain.entities

class DetailedUser(
    val name: String?,
    val company: String?,
    val publicRepos: Int,
    val hireable: Boolean?,
    id: Int,
    username: String?,
    avatarUrl: String?
) : User(
    id = id,
    username = username,
    avatarUrl = avatarUrl
)
