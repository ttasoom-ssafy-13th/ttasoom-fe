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
    val btnListener: (View,String,String) -> Unit,
) : ListAdapter<Comment, CommentAdapter.ViewHolder>(DiffCallbackCom()) {


    inner class ViewHolder(val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comment) {
            binding.itemCommentName.text = item.author
            binding.itemCommentTime.text = item.created_at
            binding.itemCommentContent.text = item.content
            binding.itemCommentModifyDelete.visibility=if(item.author=="sungjun@gmail.com")View.VISIBLE else View.INVISIBLE

            binding.itemCommentModifyDelete.setOnClickListener {
                btnListener(it,item.id,item.content)
            } // 수정버튼, 삭제버튼
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

