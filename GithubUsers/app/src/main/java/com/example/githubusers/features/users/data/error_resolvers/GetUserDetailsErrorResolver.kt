package com.example.githubusers.features.users.data.error_resolvers

import com.example.githubusers.core.data.error_resolvers.BaseApiErrorResolver
import com.example.githubusers.core.domain.errors.Error
import com.example.githubusers.features.users.domain.errors.UserDetailsError
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserDetailsErrorResolver @Inject constructor() : BaseApiErrorResolver() {
    override fun resolve(error: Throwable): Error {
        return if (error is HttpException && error.code() == 404) {
            UserDetailsError.NotFoundError(getErrorMessage(error))
        } else {
            super.resolve(error)
        }
    }

    private fun getErrorMessage(error: HttpException): String {
        val errorBody = error.response()?.errorBody()?.string()
        return if (errorBody != null) {
            parseErrorMessage(errorBody)
        } else {
            "Not Found"
        }
    }

    private fun parseErrorMessage(errorBody: String): String {
        return try {
            val jsonObject = JSONObject(errorBody)
            jsonObject.optString("message", "Not Found")
        } catch (e: Exception) {
            "Not Found"
        }
    }
}
