package com.ssafy.domain.community.usecase.community

import com.ssafy.domain.community.model.Like
import com.ssafy.domain.community.repository.CommunityRepository
import javax.inject.Inject

class PostBoardLikeUseCase @Inject constructor(
    private val repo : CommunityRepository
) {
    suspend operator fun invoke(post_id : String) : Result<Like>{
        return repo.postBoardLike(post_id)
    }

}