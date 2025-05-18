package com.ssafy.feature

import android.content.Context
import android.content.SharedPreferences

class LikedSharedPref(context: Context) {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("id_boolean_pref", Context.MODE_PRIVATE)

    // Boolean 플래그 저장
    fun setFlagForId(id: String, value: Boolean) {
        sharedPref.edit().putBoolean(id, value).apply()
    }

    fun getFlagForId(id: String): Boolean {
        return sharedPref.getBoolean(id, false)
    }

    // 좋아요 수 저장 (Int)
    fun setLikesForId(id: String, likes: Int) {
        sharedPref.edit().putInt("${id}_likes", likes).apply()
    }

    fun getLikesForId(id: String): Int {
        return sharedPref.getInt("${id}_likes", 0)
    }

    // id 관련 모든 데이터 삭제 (Boolean, Likes 모두 삭제)
    fun removeId(id: String) {
        sharedPref.edit()
            .remove(id)
            .remove("${id}_likes")
            .apply()
    }

    fun clearAll() {
        sharedPref.edit().clear().apply()
    }

    // Boolean 값만 모두 가져오기
    fun getAllFlags(): Map<String, Boolean> {
        return sharedPref.all
            .filterKeys { !it.endsWith("_likes") }
            .mapValues { it.value as Boolean }
    }

    // 좋아요 수만 모두 가져오기
    fun getAllLikes(): Map<String, Int> {
        return sharedPref.all
            .filterKeys { it.endsWith("_likes") }
            .mapKeys { it.key.removeSuffix("_likes") }
            .mapValues { it.value as Int }
    }

}
