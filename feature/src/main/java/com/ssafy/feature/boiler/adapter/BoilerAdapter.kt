package com.ssafy.feature.boiler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.domain.boiler.model.Boiler
import com.ssafy.feature.R
import com.ssafy.feature.databinding.ItemBoilerBinding

class BoilerAdapter : RecyclerView.Adapter<BoilerAdapter.BoilerViewHolder>() {

    private val items = mutableListOf<Boiler>()

    fun submitList(newItems: List<Boiler>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoilerViewHolder {
        val binding = ItemBoilerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BoilerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoilerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class BoilerViewHolder(private val binding: ItemBoilerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Boiler) {
            binding.tvCompany.text = item.companyName
            binding.tvProduct.text = item.productName

            // 이미지 로딩 (비어 있으면 기본 이미지 사용)
            val imageUrl = item.imageUrl
            if (imageUrl.isNullOrBlank()) {
                binding.imgBoiler.setImageResource(R.drawable.ic_launcher_foreground)
            } else {
                Glide.with(binding.imgBoiler.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(binding.imgBoiler)
            }
        }
    }
}

