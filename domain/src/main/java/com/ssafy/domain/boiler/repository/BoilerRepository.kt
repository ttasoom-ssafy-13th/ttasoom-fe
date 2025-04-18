package com.ssafy.domain.boiler.repository

import com.ssafy.domain.boiler.model.Boiler

interface BoilerRepository {
    suspend fun getBoilerList(): Result<List<Boiler>>
    suspend fun getFilterBoilerList(
        companyName: String?,
        certificationType: String?, // 인증 종류
        circulationType: String?, // 순환 바식
        fuelType: String?, // 사용 연료
    ) : Result<List<Boiler>>
}