package com.ssafy.domain.community.usecase.comment

import com.ssafy.domain.community.model.Comment
import com.ssafy.domain.community.repository.CommentRepository
import javax.inject.Inject

class PostCommentUseCase @Inject constructor(
    private val repo : CommentRepository
){
    suspend operator fun invoke(post_id :String, content : String) : Result<Comment>{
        return repo.postComments(post_id,content)
    }
}