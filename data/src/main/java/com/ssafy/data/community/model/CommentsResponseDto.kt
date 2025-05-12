package com.ssafy.data.community.model

import com.google.gson.annotations.SerializedName

data class CommentsResponseDto(
    @SerializedName("id")
    val id: String = "", //comment id
    @SerializedName("post_id")
    val post_id: String = "",
    @SerializedName("author")
    val author: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("created_at")
    val created_at: String = ""
)
