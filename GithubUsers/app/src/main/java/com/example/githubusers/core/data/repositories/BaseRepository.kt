package com.example.githubusers.core.data.repositories

import com.example.githubusers.core.data.error_resolvers.ErrorResolver
import com.example.githubusers.core.domain.errors.Error
import com.example.githubusers.core.domain.util.Result

abstract class BaseRepository {
    protected suspend fun <D, E : Error> execute(
        function: suspend () -> Result<D, E>,
        errorResolver: ErrorResolver<E>,
    ): Result<D, E> {
        return try {
            function()
        } catch (e: Throwable) {
            Result.Error(error = errorResolver.resolve(e))
        }
    }
}
