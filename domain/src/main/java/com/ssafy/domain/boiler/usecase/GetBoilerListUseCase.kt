package com.ssafy.domain.boiler.usecase

import com.ssafy.domain.boiler.model.Boiler
import com.ssafy.domain.boiler.repository.BoilerRepository
import javax.inject.Inject

class GetBoilerListUseCase @Inject constructor(
    private val repository: BoilerRepository
) {
    suspend operator fun invoke(): Result<List<Boiler>> {
        return repository.getBoilerList()
    }
}
