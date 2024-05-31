package com.example.githubusers.core.domain.util

sealed class Error(message: String = "") : Throwable(message = message)

class NetworkError : Error()

class ClientError : Error()

class ServerError : Error()

class UnknownError : Error()

sealed class UserDetailsError(message: String) : Error(message) {
    class NotFoundError(message: String) : UserDetailsError(message = message)
}