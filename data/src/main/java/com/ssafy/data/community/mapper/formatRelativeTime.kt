package com.ssafy.data.community.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

//시간 환산하는 함수
//ex ) 1시간 전 2시간전 1일전 2일전 이런거

@RequiresApi(Build.VERSION_CODES.O)
fun formatRelativeTime(isoTime : String) : String {

    return try {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val dateTime = OffsetDateTime.parse(isoTime, formatter)
        val now = OffsetDateTime.now(ZoneOffset.UTC)
        val duration = Duration.between(dateTime, now)

        when {
            duration.toMinutes() < 1 -> "방금 전"
            duration.toMinutes() < 60 -> "${duration.toMinutes()}분 전"
            duration.toHours() < 24 -> "${duration.toHours()}시간 전"
            duration.toDays() < 7 -> "${duration.toDays()}일 전"
            else -> dateTime.toLocalDate().toString() // 또는 "yyyy-MM-dd"
        }
    } catch (e: Exception) {
        isoTime // 파싱 실패 시 원본 반환
    }
}