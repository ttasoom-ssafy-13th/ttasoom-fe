package com.ssafy.domain.community.usercase

import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.repository.CommunityRepository
import javax.inject.Inject

class GetBoardByIdUserCase @Inject constructor(
    private val repo : CommunityRepository
){
    suspend operator fun invoke(post_id : String) : Result<Board>{
        return repo.getBoardById(post_id)
    }
}