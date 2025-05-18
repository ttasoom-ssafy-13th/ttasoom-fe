package com.ssafy.domain.community.model

data class Like(
    val detail : String,
    val like_count : Int,
    val liked_users : MutableList<String>
)
