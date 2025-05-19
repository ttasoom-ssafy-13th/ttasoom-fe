package com.ssafy.feature.community

import com.ssafy.feature.R

object ProfileImg{
    fun setImg( userAuhor : String) : Int{
        val firstChar = userAuhor.firstOrNull()?.uppercaseChar() ?: return R.drawable.profile1

        return when (firstChar) {
            in 'A'..'C' -> R.drawable.profile1   // A ~ C
            in 'D'..'F' -> R.drawable.profile2   // D ~ F
            in 'G'..'I' -> R.drawable.profile3   // G ~ I
            in 'J'..'L' -> R.drawable.profile4   // J ~ L
            in 'M'..'O' -> R.drawable.profile5   // M ~ O
            in 'P'..'S' -> R.drawable.profile6   // P ~ S
            in 'T'..'Z' -> R.drawable.profile7   // T ~ Z
            else -> R.drawable.profile1          // 기타 문자나 숫자 등
        }
    }
}

