package com.example.githubusers.core.data.error_resolvers

import com.example.githubusers.core.domain.util.ClientError
import retrofit2.HttpException
import java.io.IOException

import com.example.githubusers.core.domain.util.Error
import com.example.githubusers.core.domain.util.NetworkError
import com.example.githubusers.core.domain.util.ServerError
import com.example.githubusers.core.domain.util.UnknownError
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class BaseApiErrorResolver @Inject constructor() : ErrorResolver<Error> {
    override fun resolve(error: Throwable): Error {
        return when (error) {
            is HttpException -> {
                val code = error.code()
                when (code) {
                    in 500..599 -> ServerError()
                    in 400..499 -> ClientError()
                    else -> ClientError()
                }
            }

            is IOException -> NetworkError()
            else -> UnknownError()
        }
    }
}
