package com.ssafy.domain.community.usercase

import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.repository.CommunityRepository
import javax.inject.Inject

class GetBoardUserCase @Inject constructor(
    private val repo : CommunityRepository
) {
    suspend operator fun invoke() : Result<MutableList<Board>>{
        return repo.getBoard()
    }
}