package com.example.githubusers.features.users.data.error_resolvers

import com.example.githubusers.core.data.error_resolvers.BaseApiErrorResolver
import com.example.githubusers.core.domain.errors.Error
import com.example.githubusers.features.users.domain.errors.UsersError
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUsersErrorResolver @Inject constructor() : BaseApiErrorResolver() {
    override fun resolve(error: Throwable): Error {
        if (error is HttpException && error.code() == 403) {
            return UsersError.RateLimitExceededError(getErrorMessage(error))
        }
        return super.resolve(error)
    }

    private fun getErrorMessage(error: HttpException): String {
        val errorBody = error.response()?.errorBody()?.string()
        return if (errorBody != null) {
            parseErrorMessage(errorBody)
        } else {
            "API rate limit exceeded. Please try again later."
        }
    }

    private fun parseErrorMessage(errorBody: String): String {
        return try {
            val jsonObject = JSONObject(errorBody)
            jsonObject.optString("message", "API rate limit exceeded. Please try again later.")
        } catch (e: Exception) {
            "API rate limit exceeded. Please try again later."
        }
    }
}
