package com.ssafy.domain.community.usecase.comment

import com.ssafy.domain.community.model.Comment
import com.ssafy.domain.community.repository.CommentRepository
import javax.inject.Inject

class GetCommentUseCase @Inject constructor(
    private val repo : CommentRepository
) {
    suspend operator fun invoke(post_id :String):Result<MutableList<Comment>>{
        return repo.getComments(post_id)
    }
}