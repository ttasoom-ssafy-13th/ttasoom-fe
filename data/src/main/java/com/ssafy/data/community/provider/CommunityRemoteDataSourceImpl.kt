package com.ssafy.data.community.provider

import android.util.Log
import com.ssafy.data.community.api.CommunityApiService
import com.ssafy.data.community.model.BoardResponseDto
import javax.inject.Inject

private const val TAG = "CommunityRemoteDataSour_싸피"
class CommunityRemoteDataSourceImpl @Inject constructor(private val api: CommunityApiService) :
    CommunityRemoteDataSource {

    override suspend fun getBoard(): Result<MutableList<BoardResponseDto>> {

        return try {
            val response = api.getBoard()
            if (response.isSuccessful) Result.success(response.body() ?: mutableListOf())
            else Result.failure(Exception("unknown error : ${response.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun postBoard(title: String, content: String): Result<BoardResponseDto> {
        return try {
            val response = api.postBoard(title, content)
            if (response.isSuccessful) {
                Result.success(response.body() ?: BoardResponseDto())
            } else {
                Result.failure(Exception("unknown error : ${response.code()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBoardById(post_id: String): Result<BoardResponseDto> {
        return try{
            val response=api.getBoardById(post_id)
            if (response.isSuccessful) {
                Result.success(response.body() ?: BoardResponseDto())
            } else {
                Result.failure(Exception("unknown error : ${response.code()}"))
            }
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun putBoardById(
        post_id: String,
        title: String,
        content: String
    ): Result<BoardResponseDto> {
        return try{
            Log.d(TAG, "putBoardById: ${post_id}, ${title}, ${content}")
            val response=api.putBoardById(post_id,title,content)
            Log.d(TAG, "putBoardById!!!!!!: $response")
            if (response.isSuccessful) {
                Result.success(response.body() ?: BoardResponseDto())
            } else {
                Result.failure(Exception("unknown error : ${response.code()}"))
            }
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteBoardById(post_id: String): Result<Unit> {
        return try {
            val response = api.deleteBoardById(post_id)
            if(response.isSuccessful){
                Result.success(Unit)
            }else {
                Result.failure(Exception("unknown error : ${response.code()}"))
            }
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postBoardLike(post_id: String): Result<Unit> {
        return try {
            val response = api.postBoardLike(post_id)
            if(response.isSuccessful){
                Result.success(Unit)
            }else {
                Result.failure(Exception("unknown error : ${response.code()}"))
            }
        }catch (e: Exception) {
            Result.failure(e)
        }
    }
}