package com.ssafy.data.community.api

import com.ssafy.data.community.model.CommentsResponseDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentApiService {
    @GET("/board/{post_id}/comments")
    suspend fun getComments(@Path("post_id") post_id: String): Response<MutableList<CommentsResponseDto>>

    @POST("/board/{post_id}/comments")
    suspend fun postComments(
        @Path("post_id") post_id: String,
        @Query("content") content: String
    ): Response<CommentsResponseDto>

    @PUT("/board/{post_id}/comments/{comment_id}")
    suspend fun putComments(
        @Path("post_id") post_id: String,
        @Path("comment_id") comment_id: String,
        @Query("new_content") new_content: String
    ): Response<CommentsResponseDto>

    @DELETE("/board/{post_id}/comments/{comment_id}")
    suspend fun deleteComments(
        @Path("post_id") post_id: String,
        @Path("comment_id") comment_id: String,
    ): Response<Unit>

}