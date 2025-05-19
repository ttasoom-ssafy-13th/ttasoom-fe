package com.ssafy.data.community.api

import com.ssafy.data.community.model.BoardResponseDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CommunityApiService {

    @GET("/board")
    suspend fun getBoard(): Response<MutableList<BoardResponseDto>>

    @POST("/board")
    suspend fun postBoard(
        @Query("title") title: String,
        @Query("content") content: String
    ): Response<BoardResponseDto>

    @GET("/board/{post_id}")
    suspend fun getBoardById(
        @Path("post_id") post_id: String
    ): Response<BoardResponseDto>

    @PUT("/board/{post_id}")
    suspend fun putBoardById(
        @Path("post_id") post_id: String,
        @Query("title") title: String,
        @Query("content") content: String
    ): Response<BoardResponseDto>

    @DELETE("/board/{post_id}")
    suspend fun deleteBoardById(
        @Path("post_id") post_id: String
    ): Response<Unit>

    @POST("/board/{post_id}/like")
    suspend fun postBoardLike(
        @Path("post_id") post_id: String
    ): Response<Unit>

}