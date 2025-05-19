package com.ssafy.domain.community.repository

import com.ssafy.domain.community.model.Board

interface CommunityRepository {
    suspend fun getBoard(): Result<MutableList<Board>>

    suspend fun postBoard(title: String, content: String): Result<Board>

    suspend fun getBoardById(post_id: String): Result<Board>

    suspend fun putBoardById(
        post_id: String,
        title: String,
        content: String
    ): Result<Board>

    suspend fun deleteBoardById(post_id: String): Result<Unit>

    suspend fun postBoardLike(post_id: String): Result<Unit>
}