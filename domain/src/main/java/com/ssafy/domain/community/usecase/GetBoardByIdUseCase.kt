package com.ssafy.domain.community.usecase

import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.repository.CommunityRepository
import javax.inject.Inject

class GetBoardByIdUseCase @Inject constructor(
    private val repo : CommunityRepository
){
    suspend operator fun invoke(post_id : String) : Result<Board>{
        return repo.getBoardById(post_id)
    }
}