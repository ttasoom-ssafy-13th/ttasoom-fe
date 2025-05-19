package com.ssafy.data.community.provider

import com.ssafy.data.community.model.BoardResponseDto
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface CommunityRemoteDataSource {

    suspend fun getBoard(): Result<MutableList<BoardResponseDto>>

    suspend fun postBoard(title: String, content: String): Result<BoardResponseDto>

    suspend fun getBoardById(post_id: String): Result<BoardResponseDto>

    suspend fun putBoardById(
        post_id: String,
        title: String,
        content: String
    ): Result<BoardResponseDto>

    suspend fun deleteBoardById(post_id: String): Result<Unit>

    suspend fun postBoardLike(post_id: String): Result<Unit>
}