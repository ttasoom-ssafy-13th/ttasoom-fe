package com.ssafy.data.community.model

import com.google.gson.annotations.SerializedName

data class BoardResponseDto(
    @SerializedName("id") //게시글 아이디
    val id: String? = null,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("author")
    val author: String = "",
    @SerializedName("created_at")
    val created_at: String = "",
    @SerializedName("likedUsers")
    val likedUsers: MutableList<String> = mutableListOf()
)
