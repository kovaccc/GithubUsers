package com.example.githubusers.features.users.domain.errors

import com.example.githubusers.core.domain.errors.Error

sealed class UserDetailsError(message: String) : Error(message) {
    class NotFoundError(message: String) : UserDetailsError(message = message)
}