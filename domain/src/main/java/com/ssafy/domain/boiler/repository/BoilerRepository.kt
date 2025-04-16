package com.ssafy.domain.boiler.repository

import com.ssafy.domain.boiler.model.Boiler

interface BoilerRepository {
    suspend fun getBoilerList(): Result<List<Boiler>>
}