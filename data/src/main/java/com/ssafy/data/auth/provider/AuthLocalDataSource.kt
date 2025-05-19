package com.ssafy.data.auth.provider

import com.ssafy.data.local.PreferencesManager
import javax.inject.Inject
import javax.inject.Singleton

class AuthLocalDataSource @Inject constructor(
    private val preferencesManager: PreferencesManager
) {

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val USER_ID="user_id"
    }

    fun saveTokens(accessToken: String, refreshToken: String) {
        preferencesManager.putString(KEY_ACCESS_TOKEN, accessToken)
        preferencesManager.putString(KEY_REFRESH_TOKEN, refreshToken)
    }

    fun getAccessToken(): String? {
        return preferencesManager.getString(KEY_ACCESS_TOKEN)
    }

    fun getRefreshToken(): String? {
        return preferencesManager.getString(KEY_REFRESH_TOKEN)
    }

    fun clearTokens() {
        preferencesManager.remove(KEY_ACCESS_TOKEN)
        preferencesManager.remove(KEY_REFRESH_TOKEN)
    }

    fun setUserId(userId : String){
        preferencesManager.putString(USER_ID,userId)
    }

    fun getUserId() :String?{
        return preferencesManager.getString(USER_ID)
    }

    fun clearUserId(){
        preferencesManager.remove(USER_ID)
    }
}
