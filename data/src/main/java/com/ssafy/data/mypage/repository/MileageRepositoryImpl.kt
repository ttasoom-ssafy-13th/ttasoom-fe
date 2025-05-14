package com.ssafy.data.mypage.repository

import com.ssafy.data.mypage.mapper.toDomain
import com.ssafy.data.mypage.provider.MileageApiService
import com.ssafy.domain.mypage.model.MileageHistory
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

    override suspend fun getMileageHistory(): Result<List<MileageHistory>> {
        return try {
            val response = api.getMileageHistory()
            if (response.isSuccessful) {
                response.body()?.let { dtoList ->
                    Result.success(dtoList.map { it.toDomain() })
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("API error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun checkAttendance(boilerValue: Int): Result<MileageHistory> {
        return try {
            val response = api.checkAttendance(boilerValue)
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