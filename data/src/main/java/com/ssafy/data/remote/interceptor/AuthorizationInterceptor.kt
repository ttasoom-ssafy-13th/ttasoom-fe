package com.ssafy.data.remote.interceptor

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

        // 로그인, 회원가입 요청에는 Authorization 헤더 제외
        if (path != "/auth/login" && path != "/auth/signup") {
            val token = preferencesManager.getAccessToken()
            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
        }

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
