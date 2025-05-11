package com.ssafy.domain.community.usercase

import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.repository.CommunityRepository
import javax.inject.Inject

class PostBoardUserCase @Inject constructor(
    private val repo: CommunityRepository
) {

    suspend operator fun invoke(title: String, content: String): Result<Board> {
        return repo.postBoard(title, content)
    }
}