package com.ssafy.feature.community.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.domain.community.model.Board
import com.ssafy.feature.databinding.ItemCommunityBinding

class CommunityAdapter(
    private val items: MutableList<Board>,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding : ItemCommunityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Board){
            binding.itemCommunityUser.text=item.author
            binding.itemCommunityHeartCnt.text=item.likedUsers.size.toString()
            binding.itemCommunityTitle.text=item.title
            binding.itemCommunityContent.text=item.content
            binding.itemCommunityDate.text=item.created_at //여기까지는 server data

            binding.root.setOnClickListener{
                listener(item.id.toString())
            } // 이벤트 처리
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityAdapter.ViewHolder {
        val binding =ItemCommunityBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}