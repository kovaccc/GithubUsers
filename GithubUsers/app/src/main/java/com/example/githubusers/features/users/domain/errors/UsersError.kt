package com.example.githubusers.features.users.domain.errors

import com.example.githubusers.core.domain.errors.Error

sealed class UsersError(message: String) : Error(message) {
    class RateLimitExceededError(message: String) : UsersError(message)
}
