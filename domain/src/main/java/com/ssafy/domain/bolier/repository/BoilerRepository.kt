package com.ssafy.domain.bolier.repository

import com.ssafy.domain.bolier.model.Boiler

interface BoilerRepository {
    suspend fun getAllBoilers(): List<Boiler>

    suspend fun getSubBoilers(
        company: String,
        certification: String,
        circulationType: String,
        fuel: String
    ): List<Boiler>
}
