package com.ssafy.feature.community.adapter

import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.domain.community.model.Board
import com.ssafy.feature.databinding.ItemCommunityBinding
import com.ssafy.feature.R
import com.ssafy.feature.community.ProfileImg
import javax.inject.Inject

class CommunityAdapter(
    private val userAuthor : String,
    private val listener: (String,Boolean) -> Unit,
) : ListAdapter<Board, CommunityAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemCommunityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Board) {
            Log.d("HeartTest", "likedUsers: ${item.likedUsers}")
            //val isLiked=  item.likedUsers.contains() //토큰인지 뭔지 봐야할듯 ;;
            binding.itemCommunityUser.text = item.author
            binding.itemCommunityHeartCnt.text = item.likedUsers.size.toString()
            binding.itemCommunityTitle.text = item.title
            binding.itemCommunityContent.text = item.content
            binding.itemCommunityCommentCnt.text=item.comment_count.toString()
            binding.itemCommunityDate.text = item.created_at //여기까지는 server data
            binding.itemCommunityCommentCnt.text = item.comment_count.toString() // 댓글 개수 표시
            binding.itemCommunityProfileImg.setImageResource(ProfileImg.setImg(item.author))

            if(item.likedUsers.contains(userAuthor))
                binding.itemCommunityHeart.setImageResource(R.drawable.ic_heart_click)
            else
                binding.itemCommunityHeart.setImageResource(R.drawable.ic_heart)
            binding.root.setOnClickListener {
                listener(item.id.toString(),item.likedUsers.contains(userAuthor))
            } // 이벤트 처리


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCommunityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}