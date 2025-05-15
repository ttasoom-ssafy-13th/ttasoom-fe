package com.ssafy.data.remote.interceptor

import android.util.Log
import com.ssafy.data.local.PreferencesManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val preferencesManager: PreferencesManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        val path = originalRequest.url.encodedPath

        if (path != "/auth/login" && path != "/auth/signup") {
            val token = preferencesManager.getAccessToken()
            if (!token.isNullOrEmpty()) {
                val bearerToken = "Bearer $token"
                requestBuilder.addHeader("Authorization", bearerToken)
                Log.d("AuthorizationInterceptor", "➡️ Intercepting: $path")
                Log.d("AuthorizationInterceptor", "🛡️ Authorization Header: $bearerToken")
            } else {
                Log.d("AuthorizationInterceptor", "🛡️ No token found for $path")
            }
        }

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}
