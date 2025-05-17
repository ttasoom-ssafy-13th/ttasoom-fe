package com.ssafy.data.boiler.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.ssafy.data.boiler.mapper.toDomain
import com.ssafy.data.boiler.provider.BoilerRemoteDataSource
import com.ssafy.domain.boiler.model.Boiler
import com.ssafy.domain.boiler.model.BoilerCheckHistory
import com.ssafy.domain.boiler.model.BoilerRecommendation
import com.ssafy.domain.boiler.model.BoilerUsagePrediction
import com.ssafy.domain.boiler.repository.BoilerRepository
import javax.inject.Inject

class BoilerRepositoryImpl @Inject constructor(
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

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getUsagePrediction(): Result<BoilerUsagePrediction> {
        return remote.getUsagePrediction().map { it.toDomain() }
    }

    override suspend fun getUsageRecommendations(): Result<BoilerRecommendation> {
        return remote.getUsageRecommendations().map { it.toDomain() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCheckHistory(): Result<List<BoilerCheckHistory>> {
        return remote.getCheckHistory().map { list ->
            list.map { it.toDomain() }
        }
    }
}
