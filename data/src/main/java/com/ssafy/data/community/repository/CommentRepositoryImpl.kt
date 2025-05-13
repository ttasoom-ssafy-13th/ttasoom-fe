package com.ssafy.data.community.repository

import com.ssafy.data.community.mapper.toDomain
import com.ssafy.data.community.provider.CommentRemoteDataResource
import com.ssafy.data.community.provider.CommunityRemoteDataSource
import com.ssafy.domain.community.model.Comment
import com.ssafy.domain.community.repository.CommentRepository
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val remote: CommentRemoteDataResource
) : CommentRepository {
    override suspend fun getComments(post_id: String): Result<MutableList<Comment>> {
        return remote.getComments(post_id).map { mlist ->
            mlist.map { it.toDomain() }.toMutableList()
        }
    }

    override suspend fun postComments(post_id: String, content: String): Result<Comment> {
        return remote.postComments(post_id, content).map { comment ->
            comment.toDomain()
        }
    }

    override suspend fun putComments(
        post_id: String,
        content_id: String,
        new_content: String
    ): Result<Comment> {
        return remote.putComments(post_id, content_id, new_content).map { comment ->
            comment.toDomain()
        }
    }

    override suspend fun deleteComments(post_id: String, content_id: String): Result<Unit> {
        return remote.deleteComments(post_id, content_id)
    }

}