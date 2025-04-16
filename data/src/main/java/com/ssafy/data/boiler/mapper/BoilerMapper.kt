package com.ssafy.data.boiler.mapper

import com.ssafy.data.boiler.model.BoilerDto
import com.ssafy.domain.bolier.model.Boiler


fun BoilerDto.toDomain(): Boiler = Boiler(
    name = name ?: "",                              // 제품명
    company = company ?: "",                        // 기업명
    certification = certification ?: "",            // 인증종류
    circulationType = circulationType ?: "",        // 순환방식
    fuel = fuel ?: "",                              // 사용연료
    certificationDate = certificationDate ?: "",    // 인증 시작일
    imageUrl = imageUrl ?: ""                       // 이미지 url
)
