package com.ssafy.data.boiler.mapper

import com.ssafy.data.boiler.model.BoilerItemDto
import com.ssafy.domain.boiler.model.Boiler
fun BoilerItemDto.toDomain() = Boiler(
    companyName = companyName ?: "",
    certificationType = certificationType ?: "",
    circulationType = circulationType ?: "",
    fuelType = fuelType ?: "",
    productName = productName ?: "",
    certificationStartDate = certificationStartDate ?: "",
    imageUrl = imageUrl ?: ""
)



