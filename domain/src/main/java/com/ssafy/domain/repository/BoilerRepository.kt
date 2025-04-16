package com.ssafy.domain.repository

import com.ssafy.domain.model.Boiler
import com.ssafy.domain.model.BoilerFilter

interface BoilerRepository {
    suspend fun select(boilerFilter: BoilerFilter) : Result<MutableList<Boiler>>
}