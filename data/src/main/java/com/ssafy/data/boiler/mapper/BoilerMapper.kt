package com.ssafy.data.boiler.mapper

import com.ssafy.data.boiler.model.BoilerItemDto
import com.ssafy.domain.boiler.model.Boiler

fun BoilerItemDto.toDomain(): Boiler {
    return Boiler(
        companyName = enterprise ?: "",
        certificationType = certificationType ?: "",
        circulationType = circulationType ?: "",
        fuelType = fuelType ?: "",
        productName = productName ?: "",
        certificationStartDate = certificationDate ?: "",
        imageUrl = "" // 직접 가공하는 값
    )
}
