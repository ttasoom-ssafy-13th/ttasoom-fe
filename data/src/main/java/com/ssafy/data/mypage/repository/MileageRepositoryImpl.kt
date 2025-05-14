package com.ssafy.data.mypage.repository

import com.ssafy.data.mypage.mapper.toDomain
import com.ssafy.data.mypage.provider.MileageApiService
import com.ssafy.domain.mypage.model.MileageStatus
import com.ssafy.domain.mypage.repository.MileageRepository
import javax.inject.Inject


class MileageRepositoryImpl @Inject constructor(
    private val api: MileageApiService
) : MileageRepository {

    override suspend fun getMileageStatus(): Result<MileageStatus> {
        return try {
            val response = api.getMileageStatus()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.toDomain())
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("API error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}