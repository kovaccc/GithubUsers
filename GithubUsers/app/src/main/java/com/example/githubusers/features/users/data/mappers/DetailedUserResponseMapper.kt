package com.example.githubusers.features.users.data.mappers

import com.example.githubusers.core.data.mappers.ResponseMapper
import com.example.githubusers.features.users.data.models.DetailedUserResponse
import com.example.githubusers.features.users.domain.entities.DetailedUser
import javax.inject.Inject

class DetailedUserResponseMapper @Inject constructor() :
    ResponseMapper<DetailedUser, DetailedUserResponse> {
    override operator fun invoke(response: DetailedUserResponse): DetailedUser {
        return DetailedUser(
            id = response.id,
            username = response.login,
            avatarUrl = response.avatarUrl,
            name = response.name,
            company = response.company,
            publicRepos = response.publicRepos,
            hireable = response.hireable
        )
    }
}
