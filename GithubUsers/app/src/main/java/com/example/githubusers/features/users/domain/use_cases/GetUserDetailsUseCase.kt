package com.example.githubusers.features.users.domain.use_cases


import com.example.githubusers.features.users.domain.repositories.UsersRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserDetailsUseCase @Inject constructor(private val userRepository: UsersRepository) {
    suspend fun getUserDetails(loginName: String) = userRepository.getUserDetails(loginName)
}
