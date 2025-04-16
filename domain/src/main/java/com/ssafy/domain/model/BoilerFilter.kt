package com.ssafy.domain.model

data class BoilerFilter (
    val company : MutableList<String>?, //6종류
    val authNum : Int?,
    val operateMethod : String?,
    val fuel : MutableList<String>?, // 3종류
    val authStartDate : String?
) //분류할 항목들. 분류안할 부분은  null로 처리


//data class BoilerFilter (
//    val company : String?,
//    val authNum : Int?,
//    val operateMethod : String?,
//    val fuel : String?,
//    val authStartDate : String?
//)