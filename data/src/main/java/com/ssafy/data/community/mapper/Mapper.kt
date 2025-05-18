package com.ssafy.data.community.mapper

import android.os.Build
import com.ssafy.data.community.model.BoardResponseDto
import com.ssafy.data.community.model.CommentsResponseDto
import com.ssafy.data.community.model.LikeResponseDto
import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.model.Comment
import com.ssafy.domain.community.model.Like

fun BoardResponseDto.toDomain(): Board {
    return Board(
        id = this.id ?: "unknown",
        title = this.title,
        content = this.content,
        author = this.author,
        created_at = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatRelativeTime(this.created_at)
        } else {
            this.created_at
        },
        likedUsers = this.likedUsers,
        comment_count=this.comment_count
    )
}


fun CommentsResponseDto.toDomain(): Comment {
    return Comment(
        id = this.id,
        post_id = this.post_id,
        author = this.author,
        content = this.content,
        created_at = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatRelativeTime(this.created_at)
        } else {
            this.created_at
        }
    )
}

fun LikeResponseDto.toDomain() : Like {
    return Like(
        detail=this.detail,
        like_count=this.like_count,
        liked_users=this.liked_users
    )
}