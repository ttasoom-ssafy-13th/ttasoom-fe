package com.ssafy.domain.community.usercase

import com.ssafy.domain.community.repository.CommunityRepository
import javax.inject.Inject

class PostBoardLikeUserCase @Inject constructor(
    private val repo : CommunityRepository
) {
    suspend operator fun invoke(post_id : String) : Result<Unit>{
        return repo.postBoardLike(post_id)
    }

}