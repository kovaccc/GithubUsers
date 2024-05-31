package com.example.githubusers.core.data.error_resolvers

import com.example.githubusers.core.domain.util.Error

interface ErrorResolver<E : Error> {
    fun resolve(error: Throwable): E
}
