package com.ssafy.domain.usecase

import com.ssafy.domain.model.Boiler
import com.ssafy.domain.model.BoilerFilter
import com.ssafy.domain.repository.BoilerRepository

class BoilerUserCase(private val repository: BoilerRepository) {

    suspend fun execute(boilerFilter: BoilerFilter): Result<MutableList<Boiler>> {
        return try {
            repository.select(boilerFilter)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}