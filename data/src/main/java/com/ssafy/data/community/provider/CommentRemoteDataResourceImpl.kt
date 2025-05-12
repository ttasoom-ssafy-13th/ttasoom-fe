package com.ssafy.data.community.provider

import com.ssafy.data.community.api.CommentApiService
import com.ssafy.data.community.model.CommentsResponseDto
import javax.inject.Inject

class CommentRemoteDataResourceImpl @Inject constructor(private val api: CommentApiService) :
    CommentRemoteDataResource {

    override suspend fun getComments(post_id: String): Result<MutableList<CommentsResponseDto>> {
        return try {
            val response = api.getComments(post_id)
            if (response.isSuccessful) Result.success(response.body() ?: mutableListOf())
            else Result.failure(Exception("unknown error : ${response.code()}"))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postComments(
        post_id: String,
        content: String
    ): Result<CommentsResponseDto> {
        return try {
            val response = api.postComments(post_id, content)
            if (response.isSuccessful) Result.success(response.body() ?: CommentsResponseDto())
            else Result.failure(Exception("unknown error : ${response.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun putComments(
        post_id: String,
        content_id: String,
        new_content: String
    ): Result<CommentsResponseDto> {
        return try {
            val response = api.putComments(post_id, content_id, new_content)
            if (response.isSuccessful) Result.success(response.body() ?: CommentsResponseDto())
            else Result.failure(Exception("unknown error : ${response.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteComments(post_id: String, content_id: String): Result<Unit> {
        return try {
            val response = api.deleteComments(post_id, content_id)
            if (response.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("unknown error : ${response.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}