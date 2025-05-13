package com.ssafy.domain.community.repository

import com.ssafy.domain.community.model.Comment

interface CommentRepository {

    suspend fun getComments(post_id: String): Result<MutableList<Comment>>

    suspend fun postComments(
        post_id: String,
        content: String
    ): Result<Comment>

    suspend fun putComments(
        post_id: String,
        content_id: String,
        new_content: String
    ): Result<Comment>

    suspend fun deleteComments(
        post_id: String,
        content_id: String,
    ): Result<Unit>
}