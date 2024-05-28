package com.example.githubusers.core.data.mappers

interface ResponseMapper<Entity, Response> {
    operator fun invoke(response: Response): Entity
}
