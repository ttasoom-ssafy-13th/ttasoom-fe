package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerDto

class MockBoilerProvider : BoilerProvider {
    override suspend fun getBoilerList(): List<BoilerDto> {
        return listOf(
            BoilerDto(
                company = "(주)임코보일러",
                name = "EBP",
                certification = "2종",
                circulationType = "차단식",
                fuel = "LNG",
                certificationDate = "2020-06-26",
                imageUrl = "https://via.placeholder.com/150"
            ),
            BoilerDto(
                company = "린나이코리아(주)",
                name = "R323-10KF",
                certification = "2종",
                circulationType = "개방식",
                fuel = "LNG",
                certificationDate = "2020-06-26",
                imageUrl = "https://via.placeholder.com/150"
            ),
            BoilerDto(
                company = "린나이코리아(주)",
                name = "R331S-10KF",
                certification = "2종",
                circulationType = "개방식",
                fuel = "LNG",
                certificationDate = "2020-06-26",
                imageUrl = "https://via.placeholder.com/150"
            ),
            BoilerDto(
                company = "경동나비엔(주)",
                name = "NCB500",
                certification = "1종",
                circulationType = "차단식",
                fuel = "기름",
                certificationDate = "2020-07-10",
                imageUrl = "https://via.placeholder.com/150"
            ),
            BoilerDto(
                company = "경동나비엔(주)",
                name = "NCB700",
                certification = "2종",
                circulationType = "개방식",
                fuel = "LNG",
                certificationDate = "2021-01-15",
                imageUrl = "https://via.placeholder.com/150"
            ),
            BoilerDto(
                company = "(주)귀뚜라미보일러",
                name = "K1-20A",
                certification = "1종",
                circulationType = "차단식",
                fuel = "LPG",
                certificationDate = "2021-03-08",
                imageUrl = "https://via.placeholder.com/150"
            ),
            BoilerDto(
                company = "(주)귀뚜라미보일러",
                name = "K2-25G",
                certification = "2종",
                circulationType = "개방식",
                fuel = "LNG",
                certificationDate = "2021-05-20",
                imageUrl = "https://via.placeholder.com/150"
            ),
            BoilerDto(
                company = "대성쎌틱에너시스(주)",
                name = "DX-210F",
                certification = "1종",
                circulationType = "차단식",
                fuel = "기름",
                certificationDate = "2022-02-12",
                imageUrl = "https://via.placeholder.com/150"
            )
        )
    }
}
