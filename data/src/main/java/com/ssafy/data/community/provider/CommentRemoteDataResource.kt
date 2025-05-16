package com.ssafy.data.community.provider

import com.ssafy.data.community.model.CommentsResponseDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentRemoteDataResource {

    suspend fun getComments(post_id: String): Result<MutableList<CommentsResponseDto>>

    suspend fun postComments(
        post_id: String,
        content: String
    ): Result<CommentsResponseDto>

    suspend fun putComments(
        post_id: String,
        content_id: String,
        new_content: String
    ): Result<CommentsResponseDto>

    suspend fun deleteComments(
        post_id: String,
        content_id: String,
    ): Result<Unit>

}