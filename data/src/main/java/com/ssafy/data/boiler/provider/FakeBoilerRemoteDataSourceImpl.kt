package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerItemDto
import kotlinx.coroutines.delay

class FakeBoilerRemoteDataSourceImpl : BoilerRemoteDataSource {

    private val dummyData = listOf(
        BoilerItemDto(
            enterprise = "SSAFY Boiler Inc.",
            certificationType = "1종",
            circulationType = "자연순환",
            fuelType = "도시가스",
            productName = "SmartBoiler X100",
            certificationDate = "2023-10-01"
        ),
        BoilerItemDto(
            enterprise = "WarmTech",
            certificationType = "2종",
            circulationType = "강제순환",
            fuelType = "등유",
            productName = "HeatMaster 2000",
            certificationDate = "2022-05-15"
        )
    )

    override suspend fun getBoilerList(
        companyName: String?,
        certificationType: String?,
        circulationType: String?,
        fuelType: String?
    ): Result<List<BoilerItemDto>> {
        delay(1000)

        val filtered = dummyData.filter { item ->
            (companyName == null || item.enterprise == companyName) &&
                    (certificationType == null || item.certificationType == certificationType) &&
                    (circulationType == null || item.circulationType == circulationType) &&
                    (fuelType == null || item.fuelType == fuelType)
        }

        return Result.success(filtered)
    }
}
