package com.ssafy.domain.community.usecase.community

import com.ssafy.domain.community.repository.CommunityRepository
import javax.inject.Inject

class DeleteBoardByIdUseCase @Inject constructor(
    private val repo : CommunityRepository
) {

    suspend operator fun invoke(post_id : String) : Result<Unit>{
        return repo.deleteBoardById(post_id)
    }

}