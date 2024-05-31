package com.example.githubusers.features.users.data.error_resolvers

import com.example.githubusers.core.data.error_resolvers.BaseApiErrorResolver
import com.example.githubusers.core.domain.util.Error
import com.example.githubusers.core.domain.util.UserDetailsError
import org.json.JSONObject
import retrofit2.HttpException

class GetUserDetailsErrorResolver : BaseApiErrorResolver() {
    override fun resolve(error: Throwable): Error {
        return if (error is HttpException && error.code() == 404) {
            val errorBody = error.response()?.errorBody()?.string()
            val message = if (errorBody != null) {
                try {
                    val jsonObject = JSONObject(errorBody)
                    jsonObject.optString("message", "Not Found")
                } catch (e: Exception) {
                    "Not Found"
                }
            } else {
                "Not Found"
            }
            UserDetailsError.NotFoundError(message)
        } else {
            super.resolve(error)
        }
    }
}