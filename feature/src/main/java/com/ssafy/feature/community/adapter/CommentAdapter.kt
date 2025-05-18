package com.ssafy.feature.community.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.model.Comment
import com.ssafy.feature.databinding.ItemCommentBinding
import com.ssafy.feature.databinding.ItemCommunityBinding

class CommentAdapter(
    val editListener: (String) -> Unit,
    val removeListener: (String) -> Unit,
) : ListAdapter<Comment, CommentAdapter.ViewHolder>(DiffCallbackCom()) {

    private var openedCommentId: String? = null

    inner class ViewHolder(val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comment) {
            binding.itemCommentName.text = item.author
            binding.itemCommentTime.text = item.created_at
            binding.itemCommentContent.text = item.content

            binding.itemCommentModify.setOnClickListener {
                editListener(item.id)
            } // 수정버튼


            binding.itemCommentDelete.setOnClickListener {
                removeListener(item.id)
            } //삭제 버튼

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
        val binding =
            ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

