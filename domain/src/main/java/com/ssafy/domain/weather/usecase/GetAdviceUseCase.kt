package com.ssafy.domain.weather.usecase

import com.ssafy.domain.weather.model.Advice
import com.ssafy.domain.weather.repository.WeatherRepository
import javax.inject.Inject

class GetAdviceUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): Result<Advice> {
        return repository.getAdvice(lat, lon)
    }
}
