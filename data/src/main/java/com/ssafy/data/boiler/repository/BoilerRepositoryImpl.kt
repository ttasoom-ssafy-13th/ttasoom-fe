package com.ssafy.data.boiler.repository

import com.ssafy.data.boiler.mapper.toDomain
import com.ssafy.data.boiler.provider.BoilerRemoteDataSource
import com.ssafy.domain.boiler.model.Boiler
import com.ssafy.domain.boiler.repository.BoilerRepository

class BoilerRepositoryImpl(
    private val remote: BoilerRemoteDataSource
) : BoilerRepository {

    override suspend fun getBoilerList(): Result<List<Boiler>> {
        return remote.getBoilerList().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getFilterBoilerList(
        companyNames: List<String>?,
        certificationTypes: List<String>?,
        circulationTypes: List<String>?,
        fuelTypes: List<String>?
    ): Result<List<Boiler>> {
        // ✅ 수정된 코드
        return remote.getBoilerList(
            companyNames, certificationTypes, circulationTypes, fuelTypes
        ).map { dtoList ->
            dtoList.map { it.toDomain() }
        }

    }

}
