package com.ssafy.data.boiler.model

import com.google.gson.annotations.SerializedName

data class BoilerItemDto(
    val companyName: String?,
    val certificationType: String?,
    val circulationType: String?,
    val fuelType: String?,
    val productName: String?,
    val certificationStartDate: String?,
    val imageUrl: String?
)

