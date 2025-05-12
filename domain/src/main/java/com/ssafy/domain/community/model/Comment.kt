package com.ssafy.domain.community.model

data class Comment(
    val id : String,
    val post_id : String,
    val author : String,
    val content : String,
    val created_at : String
)
