package com.ssafy.domain.community.usecase.community

import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.repository.CommunityRepository
import javax.inject.Inject

class PostBoardUseCase @Inject constructor(
    private val repo: CommunityRepository
) {

    suspend operator fun invoke(title: String, content: String): Result<Board> {
        return repo.postBoard(title, content)
    }
}