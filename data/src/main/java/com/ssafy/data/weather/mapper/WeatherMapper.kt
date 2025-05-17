package com.ssafy.data.weather.mapper

import com.ssafy.data.weather.model.AdviceResponseDto
import com.ssafy.data.weather.model.WeatherDto
import com.ssafy.domain.weather.model.Advice
import com.ssafy.domain.weather.model.Weather

fun WeatherDto.toDomain(): Weather {
    return Weather(
        minTemp = this.minTemp,
        maxTemp = this.maxTemp,
        humidity = this.humidity
    )
}

fun AdviceResponseDto.toDomain(): Advice {
    return Advice(content = this.advice)
}
