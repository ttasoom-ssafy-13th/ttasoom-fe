package com.ssafy.feature.community.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.model.Comment

class DiffCallback: DiffUtil.ItemCallback<Board>() {
    override fun areItemsTheSame(oldItem: Board, newItem: Board): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Board, newItem: Board): Boolean {
        return oldItem == newItem
    }
}

