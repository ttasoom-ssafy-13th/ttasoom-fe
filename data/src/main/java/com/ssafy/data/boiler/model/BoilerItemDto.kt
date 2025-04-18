package com.ssafy.data.boiler.model

import com.google.gson.annotations.SerializedName

data class BoilerItemDto(
    @SerializedName("기업명")
    val enterprise: String?, // 기업명

    @SerializedName("인증종류")
    val certificationType: String?, // 인증 종류

    @SerializedName("순환방식")
    val circulationType: String?, // 순환 방식

    @SerializedName("사용연료")
    val fuelType: String?, // 사용 연료

    @SerializedName("상표명")
    val productName: String?, // 제품명

    @SerializedName("인증일")
    val certificationDate: String? // 인증 시작일
)
