package com.example.githubusers.features.users.domain.use_cases

import com.example.githubusers.features.users.domain.repositories.UsersRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(private val userRepository: UsersRepository) {
    suspend operator fun invoke(loginName: String) = userRepository.getUserDetails(loginName)
}
