package com.ssafy.domain.community.usecase.comment

import com.ssafy.domain.community.model.Comment
import com.ssafy.domain.community.repository.CommentRepository
import javax.inject.Inject

class PutCommentUseCase @Inject constructor(
    private val repo: CommentRepository
) {
    suspend operator fun invoke(
        post_id: String,
        content_id: String,
        new_content: String
    ): Result<Comment> {
        return repo.putComments(post_id, content_id, new_content)
    }
}