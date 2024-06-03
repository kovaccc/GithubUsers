package com.example.githubusers.core.data.error_resolvers

import com.example.githubusers.core.domain.errors.Error

interface ErrorResolver<E : Error> {
    fun resolve(error: Throwable): E
}
