package com.ssafy.feature.community.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.model.Comment
import com.ssafy.feature.R
import com.ssafy.feature.databinding.ItemBoardBinding
import com.ssafy.feature.databinding.ItemCommentBinding

class BoardAdapter(
    private val board: Board,
    private val list: MutableList<Comment>,
    val clickLikeListener :()-> Unit,
    val editListener: (String,String) -> Unit,
    val removeListener : (String)->Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var openedCommentId: String? = null

    companion object {
        private const val VIEW_TYPE_BOARD = 0
        private const val VIEW_TYPE_COMMENT = 1
    }

    inner class ViewHolderByBoard(private val binding: ItemBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Board) {
            binding.communityUser.text = item.author
            binding.communityDate.text = item.created_at
            binding.communityTitle.text = item.title
            binding.communityContent.text = item.content
            binding.communityCommentCnt.text = item.comment_count.toString()
            binding.communityHeartCnt.text = item.likedUsers.size.toString()

            binding.communityHeart.setOnClickListener{
                val currentHeartCnt= binding.communityHeartCnt.text.toString().toInt()

                binding.communityHeartCnt.text=(currentHeartCnt+1).toString()
                binding.communityHeart.setImageResource(R.drawable.ic_heart_click)
                clickLikeListener
            }
        }
    }

    inner class ViewHolderByComment(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Comment) {
            binding.itemCommentName.text = item.author
            binding.itemCommentTime.text = item.created_at
            binding.itemCommentContent.text = item.content

            val isOpened = item.id == openedCommentId
            binding.itemCommentEditLayout.visibility =
                if (isOpened) View.VISIBLE else View.GONE // 수정 버튼 누를 때 editText 띄우는 것 관리

            binding.itemCommentModify.setOnClickListener {
                openedCommentId = item.id.takeUnless { it == openedCommentId } // 참이면 null, 거짓이면 item_id반환
                notifyItemRangeChanged(0, itemCount)
                binding.itemCommentEditText.setText("")
            } // 수정버튼 누를때 밑에 editText띄우는 용도

            binding.itemCommentSendText.setOnClickListener{
                val newComment=binding.itemCommentEditText.text.toString()
                editListener(item.id,newComment)
            } //수정하고 확인 버튼 누르는 동작

            binding.itemCommentDelete.setOnClickListener{
                removeListener(item.id)
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
            val comment = list[position-1]
            holder.bind(comment)
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1;
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_BOARD else VIEW_TYPE_COMMENT
    }

    fun updateComments(newComments: List<Comment>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = list.size
            override fun getNewListSize() = newComments.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return list[oldItemPosition].id == newComments[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return list[oldItemPosition] == newComments[newItemPosition]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newComments)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getBoardTitle() = board.title
    fun getBoardContent() =board.content


}