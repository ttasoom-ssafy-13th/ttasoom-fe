package com.ssafy.data.boiler.mapper

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.ssafy.data.boiler.model.BoilerCheckHistoryDto
import com.ssafy.data.boiler.model.BoilerItemDto
import com.ssafy.data.boiler.model.BoilerRecommendationDto
import com.ssafy.data.boiler.model.BoilerUsagePredictionDto
import com.ssafy.domain.boiler.model.Boiler
import com.ssafy.domain.boiler.model.BoilerCheckHistory
import com.ssafy.domain.boiler.model.BoilerRecommendation
import com.ssafy.domain.boiler.model.BoilerUsagePrediction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun BoilerItemDto.toDomain() = Boiler(
    companyName = companyName ?: "",
    certificationType = certificationType ?: "",
    circulationType = circulationType ?: "",
    fuelType = fuelType ?: "",
    productName = productName ?: "",
    certificationStartDate = certificationStartDate ?: "",
    imageUrl = imageUrl ?: ""
)

@RequiresApi(Build.VERSION_CODES.O)
fun BoilerUsagePredictionDto.toDomain() = BoilerUsagePrediction(
    predictedUsage = predictedUsage,
    predictedCost = predictedCost,
    baseFee = baseFee,
    unitPrice = unitPrice,
    vatRate = vatRate,
    vatAmount = vatAmount,
    totalCost = totalCost,
    lastCheckDate = LocalDateTime.parse(lastCheckDate, DateTimeFormatter.ISO_DATE_TIME),
    daysSinceLastCheck = daysSinceLastCheck,
    hasSufficientHistory = hasSufficientHistory,
    message = message
)

fun BoilerRecommendationDto.toDomain(): BoilerRecommendation {
    var evaluation = ""
    var tips = ""
    var savings = ""
    var precautions = ""

    recommendations.forEach { recommendation ->
        when {
            recommendation.startsWith("[평가]") -> evaluation = recommendation.substringAfter("[평가]\n")
            recommendation.startsWith("[절감 팁]") -> tips = recommendation.substringAfter("[절감 팁]\n")
            recommendation.startsWith("[예상 절약량]") -> savings = recommendation.substringAfter("[예상 절약량]\n")
            recommendation.startsWith("[추가 주의사항]") -> precautions = recommendation.substringAfter("[추가 주의사항]\n")
        }
    }

    Log.d("nonani", "$recommendations")
    return BoilerRecommendation(
        predictedUsage = "예상 사용량: ${currentUsage.toInt()} m³",
        predictedCost = "예상 비용: ${estimatedSavings}",
        evaluation = evaluation,
        tips = tips,
        savings = savings,
        precautions = precautions
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun BoilerCheckHistoryDto.toDomain() = BoilerCheckHistory(
    boilerValue = boilerValue,
    createdAt = LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME)
)



