package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerItemDto
import kotlinx.coroutines.delay

class FakeBoilerRemoteDataSourceImpl : BoilerRemoteDataSource {
    override suspend fun getBoilerList(): Result<List<BoilerItemDto>> {
        delay(1000)
        return Result.success(
            listOf(
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
        )
    }
}
