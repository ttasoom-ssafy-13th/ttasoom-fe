package com.ssafy.data.community.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.ssafy.data.community.model.BoardResponseDto
import com.ssafy.domain.community.model.Board

fun BoardResponseDto.toDomain(): Board {
    return Board(
        id = this.id ?: "",
        title = this.title,
        content = this.content,
        author = this.author,
        created_at = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatRelativeTime(this.created_at)
        } else {
            this.created_at
        },
        likedUsers = this.likedUsers
    )
}