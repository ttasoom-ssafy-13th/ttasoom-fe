package com.ssafy.data.auth.model

data class BoilerDto (
    val boilerImg : String?,
    val companyName : String,
    val boilerName : String,
    val authNum : Int, //인증 종류
    val operateMethod : String, //순환 방식
    val fuel : String,
    val authStartDay : String,
)