package com.ssafy.feature.community.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.model.Comment
import com.ssafy.feature.databinding.ItemBoardBinding
import com.ssafy.feature.databinding.ItemCommentBinding

class BoardAdapter(
    private val board: Board,
    private val list: MutableList<Comment>,
    val listener: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_BOARD = 0
        private const val VIEW_TYPE_COMMENT = 1
    }

    inner class ViewHolderByBoard(private val binding: ItemBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Board) {
            binding.communityUser.text = item.author
            binding.communityDate.text=item.created_at
            binding.communityTitle.text=item.title
            binding.communityContent.text=item.content
            //binding.communityCommentCnt.text=item.likedUsers.size.toString()
            binding.communityHeartCnt.text=item.likedUsers.size.toString()
        }
    }

    inner class ViewHolderByComment(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Comment) {
            binding.itemCommentName.text=item.author
            binding.itemCommentTime.text=item.created_at
            binding.itemCommentContent.text=item.content

            binding.root.setOnClickListener{
                listener(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_BOARD) {
            val binding =
                ItemBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolderByBoard(binding)
        } else {
            val binding =
                ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolderByComment(binding)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderByBoard) {
            holder.bind(board)
        } else if (holder is ViewHolderByComment) {
            val comment = list[position - 1]
            holder.bind(comment)
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1;
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_BOARD else VIEW_TYPE_COMMENT
    }

}