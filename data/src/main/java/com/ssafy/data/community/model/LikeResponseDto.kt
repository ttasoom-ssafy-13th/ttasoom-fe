package com.ssafy.data.community.model

import com.google.gson.annotations.SerializedName

data class LikeResponseDto(
    @SerializedName("detail")
    val detail : String,
    @SerializedName("like_count")
    val like_count : Int,
    @SerializedName("liked_users")
    val liked_users : MutableList<String>
)
