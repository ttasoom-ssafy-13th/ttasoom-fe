package com.ssafy.data.local

import android.content.Context
import javax.inject.Inject

class PreferencesManager @Inject constructor(
    context: Context
) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return prefs.getString(key, null)
    }

    fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }

    fun getAccessToken(): String? {
        return prefs.getString("access_token", null)
    }

    fun clearAccessToken() {
        prefs.edit().remove("access_token").apply()
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}
