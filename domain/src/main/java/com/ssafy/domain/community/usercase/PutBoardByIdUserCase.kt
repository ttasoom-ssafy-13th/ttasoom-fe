package com.ssafy.domain.community.usercase

import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.repository.CommunityRepository
import javax.inject.Inject

class PutBoardByIdUserCase @Inject constructor(
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