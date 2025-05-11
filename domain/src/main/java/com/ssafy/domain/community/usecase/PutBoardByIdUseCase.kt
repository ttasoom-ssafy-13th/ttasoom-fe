package com.ssafy.domain.community.usecase

import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.repository.CommunityRepository
import javax.inject.Inject

class PutBoardByIdUseCase @Inject constructor(
    private val repo : CommunityRepository
) {

    suspend operator fun invoke(
        post_id : String,
        title : String,
        content : String
    ) : Result<Board>{
        return repo.putBoardById(post_id,title,content)
    }

}