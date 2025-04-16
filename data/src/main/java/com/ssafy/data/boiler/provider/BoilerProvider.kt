package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerDto

interface BoilerProvider {
    suspend fun getBoilerList(): List<BoilerDto>
}
