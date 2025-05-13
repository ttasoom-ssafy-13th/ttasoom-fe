package com.ssafy.data.community.repository

import com.ssafy.data.community.mapper.toDomain
import com.ssafy.data.community.provider.CommunityRemoteDataSource
import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.repository.CommunityRepository
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val remote: CommunityRemoteDataSource
) : CommunityRepository {

    override suspend fun getBoard(): Result<MutableList<Board>> {
        return remote.getBoard().map { mlist->
            mlist.map { it.toDomain() }.toMutableList()
        }
    }

    override suspend fun postBoard(title: String, content: String): Result<Board> {
        return remote.postBoard(title,content).map{ board->
            board.toDomain()
        }
    }

    override suspend fun getBoardById(post_id: String): Result<Board> {
        return remote.getBoardById(post_id).map{ board->
            board.toDomain()
        }
    }

    override suspend fun putBoardById(
        post_id: String,
        title: String,
        content: String
    ): Result<Board> {
        return remote.putBoardById(post_id,title,content).map{ board->
            board.toDomain()
        }
    }

    override suspend fun deleteBoardById(post_id: String): Result<Unit> {
        return remote.deleteBoardById(post_id)
    }

    override suspend fun postBoardLike(post_id: String): Result<Unit> {
        return remote.postBoardLike(post_id)
    }

}