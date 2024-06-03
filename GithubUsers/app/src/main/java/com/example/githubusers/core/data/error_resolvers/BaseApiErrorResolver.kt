package com.example.githubusers.core.data.error_resolvers

import com.example.githubusers.core.domain.errors.ClientError
import retrofit2.HttpException
import java.io.IOException

import com.example.githubusers.core.domain.errors.Error
import com.example.githubusers.core.domain.errors.NetworkError
import com.example.githubusers.core.domain.errors.ServerError
import com.example.githubusers.core.domain.errors.UnknownError
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class BaseApiErrorResolver @Inject constructor() : ErrorResolver<Error> {
    override fun resolve(error: Throwable): Error {
        return when (error) {
            is HttpException -> {
                when (error.code()) {
                    in 500..599 -> ServerError()
                    in 400..499 -> ClientError()
                    else -> UnknownError()
                }
            }

            is IOException -> NetworkError()
            else -> UnknownError()
        }
    }
}
