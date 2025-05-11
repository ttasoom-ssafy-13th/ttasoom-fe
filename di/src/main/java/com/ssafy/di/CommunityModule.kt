package com.ssafy.di

import com.ssafy.data.community.api.CommunityApiService
import com.ssafy.data.community.provider.CommunityRemoteDataSource
import com.ssafy.data.community.provider.CommunityRemoteDataSourceImpl
import com.ssafy.data.community.repository.CommunityRepositoryImpl

import com.ssafy.domain.community.repository.CommunityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CommunityModule {

    @Provides
    fun provideCommunityRemoteDataSource(api : CommunityApiService): CommunityRemoteDataSource {
        return CommunityRemoteDataSourceImpl(api)
    }

    @Provides
    fun provideCommunityRepository(remote : CommunityRemoteDataSource): CommunityRepository {
        return CommunityRepositoryImpl(remote)
    }

//    @Provides
//    fun provideCommunityUseCases(
//        getBoard: GetBoardUserCase,
//        deleteBoardById: DeleteBoardByIdUserCase,
//        postBoard: PostBoardUserCase,
//        getBoardById: GetBoardByIdUserCase,
//        postBoardLike: PostBoardLikeUserCase,
//        putBoard: PutBoardByIdUserCase
//    ): BoardUserCase {
//        return BoardUserCase(
//            getBoard = getBoard,
//            deleteBoardById = deleteBoardById,
//            postBoard = postBoard,
//            getBoardById = getBoardById,
//            postBoardLike = postBoardLike,
//            putBoard = putBoard
//        )
//    }
//
//    // 나머지 UseCase들 제공
//    @Provides
//    fun provideGetBoardUserCase(repo: CommunityRepository): GetBoardUserCase {
//        return GetBoardUserCase(repo)
//    }
//
//    @Provides
//    fun provideDeleteBoardByIdUserCase(repo: CommunityRepository): DeleteBoardByIdUserCase {
//        return DeleteBoardByIdUserCase(repo)
//    }
//
//    @Provides
//    fun providePostBoardUserCase(repo: CommunityRepository): PostBoardUserCase {
//        return PostBoardUserCase(repo)
//    }
//
//    @Provides
//    fun provideGetBoardByIdUserCase(repo: CommunityRepository): GetBoardByIdUserCase {
//        return GetBoardByIdUserCase(repo)
//    }
//
//    @Provides
//    fun providePostBoardLikeUserCase(repo: CommunityRepository): PostBoardLikeUserCase {
//        return PostBoardLikeUserCase(repo)
//    }
//
//    @Provides
//    fun providePutBoardByIdUserCase(repo: CommunityRepository): PutBoardByIdUserCase {
//        return PutBoardByIdUserCase(repo)
//    }

}