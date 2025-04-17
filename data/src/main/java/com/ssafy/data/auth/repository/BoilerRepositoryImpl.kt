package com.ssafy.data.auth.repository

import com.ssafy.data.auth.api.boilerApi
import com.ssafy.domain.model.Boiler
import com.ssafy.domain.model.BoilerFilter
import com.ssafy.domain.repository.BoilerRepository
import javax.inject.Inject

class BoilerRepositoryImpl @Inject constructor(
    private val api : boilerApi
) : BoilerRepository{
    override suspend fun select(boilerFilter: BoilerFilter): Result<MutableList<Boiler>> {
        val fakeDto = Boiler(
            boilerImg = "https://fake.url/image.png",
            companyName = "TestCompany",
            boilerName = "TestBoiler 9000",
            authNum = 1,
            operateMethod = "자동",
            fuel = "가스",
            authStartDay = "2024-01-01"
        )

        val list= mutableListOf<Boiler>()
        list.add(fakeDto)
        return Result.success(list)
    }

}