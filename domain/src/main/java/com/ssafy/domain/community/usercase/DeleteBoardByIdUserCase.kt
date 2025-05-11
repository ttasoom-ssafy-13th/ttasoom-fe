package com.ssafy.domain.community.usercase

import com.ssafy.domain.community.repository.CommunityRepository
import javax.inject.Inject

class DeleteBoardByIdUserCase @Inject constructor(
    private val repo : CommunityRepository
) {

    suspend operator fun invoke(post_id : String) : Result<Unit>{
        return repo.deleteBoardById(post_id)
    }

}