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
        companyName: String?,
        certificationType: String?,
        circulationType: String?,
        fuelType: String?
    ): Result<List<Boiler>> {
        return remote.getBoilerList(
            companyName = companyName,
            certificationType = certificationType,
            circulationType = circulationType,
            fuelType = fuelType
        ).map { list ->
            list.map { it.toDomain() }
        }
    }
}
