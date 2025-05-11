package com.ssafy.domain.community.model

data class Board(
    val id: String?,
    val title: String,
    val content: String,
    val author: String,
    val created_at: String,
    val likedUsers: MutableList<String>
)
