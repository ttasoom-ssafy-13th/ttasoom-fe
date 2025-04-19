package com.ssafy.domain.login.model

data class User(
    val uid: String,
    val email: String,
    val provider: AuthProviderType
)

enum class AuthProviderType {
    EMAIL, GOOGLE, KAKAO, NAVER
}
