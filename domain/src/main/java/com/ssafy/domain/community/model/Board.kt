package com.ssafy.domain.community.model

data class Board(
    val id: String?="",
    var title: String="",
    var content: String="",
    val author: String="",
    val created_at: String="",
    val likedUsers: MutableList<String> = mutableListOf(),
    var comment_count : Int = 0
)
