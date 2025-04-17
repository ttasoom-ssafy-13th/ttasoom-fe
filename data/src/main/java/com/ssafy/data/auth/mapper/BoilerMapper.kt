package com.ssafy.data.auth.mapper

import com.ssafy.data.auth.model.BoilerDto
import com.ssafy.domain.model.Boiler

fun BoilerDto.toEntity() = Boiler(
    boilerImg = this.boilerImg ?: "",
    companyName = this.companyName,
    boilerName = this.boilerName,
    authNum = this.authNum,
    operateMethod = this.operateMethod,
    fuel = this.fuel,
    authStartDay = this.authStartDay
)
