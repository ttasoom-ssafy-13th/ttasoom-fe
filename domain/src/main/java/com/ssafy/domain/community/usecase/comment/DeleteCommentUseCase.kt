package com.ssafy.domain.community.usecase.comment

import com.ssafy.domain.community.repository.CommentRepository
import javax.inject.Inject

class DeleteCommentUseCase @Inject constructor(
    private val repo : CommentRepository
) {

    suspend operator fun invoke(post_id : String, comment_id :String) : Result<Unit>{
        return repo.deleteComments(post_id,comment_id)
    }
}